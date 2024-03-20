package surveasy.domain.response.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.response.batch.JobLauncherService;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseUpdateRequestDTO;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.exception.ResponseCannotBeUpdated;
import surveasy.domain.response.exception.ResponseDuplicateData;
import surveasy.domain.response.exception.ResponseNotFound;
import surveasy.domain.response.exception.ResponseUnauthorized;
import surveasy.domain.response.mapper.ResponseMapper;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.domain.response.vo.ResponseHistoryVo;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.exception.SurveyExpired;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.exception.SurveyNotReleased;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.global.common.dto.PageInfo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ResponseHelper {

    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;

    private final SurveyHelper surveyHelper;

    private final PanelHelper panelHelper;
    private final ResponseMapper responseMapper;
    private final JobLauncherService jobLauncherService;

    public PageInfo getPageInfo(int pageNum, int pageSize, Page<?> responses) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalElements(responses.getTotalElements())
                .totalPages(responses.getTotalPages())
                .build();
    }

    /* 기존에 제출한 응답 존재 여부 확인 */
    private boolean checkAlreadyExists(Long panelId, Long surveyId) {
        Response response = responseRepository.findByPanelIdAndSurveyId(panelId, surveyId).orElse(null);
        return response != null;
    }


    /* [App] Home - 현재까지 참여한 설문 개수 */
    public long getPanelResponseCount(Long panelId) {
        return responseRepository.countByPanelIdAndStatusNot(panelId, ResponseStatus.WRONG);
    }


    /* [App] 응답 생성하기 (설문 참여 완료하기) */
    public Long createResponse(Panel panel, Long surveyId, ResponseCreateRequestDTO responseCreateRequestDTO) {
        final Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);

        // 종료된 설문인지 확인
        if(survey.getStatus().equals(SurveyStatus.DONE)) {
            throw SurveyExpired.EXCEPTION;
        }

        // 검수 전 설문인지 확인
        else if(survey.getStatus().equals(SurveyStatus.CREATED) || survey.getStatus().equals(SurveyStatus.WAITING)) {
            throw SurveyNotReleased.EXCEPTION;
        }

        // 이미 제출한 응답 있는지 확인
        if(checkAlreadyExists(panel.getId(), surveyId)) {
            throw ResponseDuplicateData.EXCEPTION;
        }

        /* 설문 응답 생성 */
        Response newResponse = responseMapper.toEntity(panel, survey, responseCreateRequestDTO);
        Response savedResponse = responseRepository.save(newResponse);

        /* 설문 응답수 업데이트, 응답수 다 찬 경우 status 업데이트 */
        Integer afterResponseCount = surveyHelper.updateCurrentHeadCount(survey);
        if(afterResponseCount >= survey.getHeadCount().getValue() + 5) {
            surveyHelper.updateStatusToDone(survey);
        }

        /* 패널 정보 업데이트 (rewardCurrent, lastParticipatedDate) */
        panelHelper.updatePanelInfoAfterResponse(panel, survey.getReward());

        return savedResponse.getId();
    }


    /* [App] MyPage - 제출한 응답 리스트 불러오기
     * 정산 예정 : before, 정산 완료 : after */
    public ResponseHistoryListResponse getResponseMyPageList(Long panelId, String type, Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        Page<ResponseHistoryVo> responseHistoryVos = responseRepository.findByPanelIdAndStatusType(panelId, type, pageable);
        PageInfo pageInfo = getPageInfo(pageNum, pageSize, responseHistoryVos);

        List<ResponseHistoryVo> responseList = new ArrayList<>();
        if(responseHistoryVos.hasContent()) {
            responseList = responseHistoryVos.getContent();
        }

        return responseMapper.toResponseHistoryList(type, responseList, pageInfo);
    }


    /* [App] MyPage - 응답 사진 변경
    * 설문 status가 IN_PROGRESS인 경우만 변경 가능
    * WRONG에서 CREATED로 업데이트하는 경우에만 적립금 반환 */
    public Long updateResponseImgUrl(Panel panel, Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> ResponseNotFound.EXCEPTION);

        // 응답한 패널이 아닌데 수정하려는 경우
        if(!Objects.equals(panel.getId(), response.getPanel().getId())) {
            throw ResponseUnauthorized.EXCEPTION;
        }

        // 이미 종료된 설문의 응답을 수정하려는 경우
        if(response.getSurvey().getStatus().equals(SurveyStatus.DONE) || response.getSurvey().getDueDate().isBefore(LocalDateTime.now())) {
            throw SurveyExpired.EXCEPTION;
        }

        // CREATED or UPDATED 응답만 수정 가능
        if(response.getStatus().equals(ResponseStatus.CREATED) || response.getStatus().equals(ResponseStatus.UPDATED)) {
            response.setImgUrl(responseUpdateRequestDTO.getImgUrl());
            response.setStatus(ResponseStatus.UPDATED);
            return responseRepository.save(response).getId();
        }

        throw ResponseCannotBeUpdated.EXCEPTION;
    }

    /* [Admin] 어드민 설문 응답 업데이트 (이미지 검수 후) */
    public Long updateResponseAdmin(Long responseId, ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> ResponseNotFound.EXCEPTION);

        if(responseUpdateRequestDTO.getStatus() != null && response.getSentAt() == null) {
            // CREATED or UPDATED -> WRONG
            if((response.getStatus().equals(ResponseStatus.CREATED) || response.getStatus().equals(ResponseStatus.UPDATED)) && responseUpdateRequestDTO.getStatus().equals(ResponseStatus.WRONG)) {
                Panel panel = response.getPanel();
                panel.setRewardCurrent(panel.getRewardCurrent() - response.getReward());
            }

            // WRONG -> CREATED
            else if(response.getStatus().equals(ResponseStatus.WRONG) && responseUpdateRequestDTO.getStatus().equals(ResponseStatus.CREATED)) {
                Panel panel = response.getPanel();
                panel.setRewardCurrent(panel.getRewardCurrent() + response.getReward());
            }
            response.setStatus(responseUpdateRequestDTO.getStatus());
        }

        return response.getId();
    }

    /* [Admin] 어드민 설문 1건의 전체 응답 목록 */
    public List<Response> getAdminSurveyResponseList(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);

        return responseRepository.findAllBySurveyIdOrderByIdDesc(surveyId);
    }

    /* [Admin] 어드민 송금 후 정산 완료 처리 */
    public void doneAggregation() {
        jobLauncherService.runJobs();
    }
}
