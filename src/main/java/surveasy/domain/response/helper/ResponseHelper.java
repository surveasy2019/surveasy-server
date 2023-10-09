package surveasy.domain.response.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseImgUrlUpdateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseMyPageListResponse;
import surveasy.domain.response.exception.ResponseDuplicateData;
import surveasy.domain.response.exception.ResponseNotFound;
import surveasy.domain.response.exception.ResponseUnauthorized;
import surveasy.domain.response.mapper.ResponseMapper;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.exception.SurveyExpired;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.exception.SurveyNotReleased;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.global.common.SurveyOptions;
import surveasy.global.common.dto.PageInfo;

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

    public PageInfo getPageInfo(int pageNum, int pageSize, Page<Response> responses) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalElements(responses.getTotalElements())
                .totalPages(responses.getTotalPages())
                .build();
    }


    // [App] Home - 현재까지 참여한 설문 개수
    public Long getPanelResponseCount(Long panelId) {
        return responseRepository.countByPanelIdAndIsValidEquals(panelId, true);
    }


    // [App] List - 이미 응답한 설문인지 확인
    private boolean checkAlreadyExists(Long panelId, Long surveyId) {
        Response response = responseRepository.findByPidAndSidAndIsValid(panelId, surveyId, true);

        if(response != null) return true;
        return false;
    }


    // [App] 응답 건수 생성하기 (설문 참여 완료하기)
    public Long createResponse(Panel panel, Long surveyId, ResponseCreateRequestDTO responseCreateRequestDTO) {
        final Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> {
                    throw SurveyNotFound.EXCEPTION;
                });

        // 종료된 설문인지 확인
        if(survey.getProgress() > 2) {
            throw SurveyExpired.EXCEPTION;
        }

        // 검수 전 설문인지 확인
        else if(survey.getProgress() < 2) {
            throw SurveyNotReleased.EXCEPTION;
        }

        // 이미 제출한 유효 응답 있는지 확인
        if(checkAlreadyExists(panel.getId(), surveyId)) {
            throw ResponseDuplicateData.EXCEPTION;
        }

        /* 설문 응답 건수 생성 */
        Response newResponse = responseMapper.toEntity(panel, survey, responseCreateRequestDTO);
        Response savedResponse = responseRepository.save(newResponse);

        /* 설문 응답수 업데이트, 응답수 다 찬 경우 progress 업데이트 */
        Integer afterResponseCount = surveyHelper.updateCurrentHeadCount(survey);
        if(afterResponseCount >= SurveyOptions.headCountValues[survey.getHeadCount()] + 5) {
            surveyHelper.updateProgress2To3(survey);
        }

        /* 패널 정보 업데이트 (rewardCurrent, lastParticipatedDate) */
        panelHelper.updatePanelInfoAfterResponse(panel, survey.getReward());

        return savedResponse.getId();
    }


    // [App] MyPage - 제출한 응답 리스트 불러오기
    /* 정산 예정 : before, 정산 완료 : after */
    public ResponseMyPageListResponse getResponseMyPageList(Long panelId, String type, Pageable pageable) {
        Boolean isSent = !type.equals("before");
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize);
        Page<Response> responses = responseRepository.findAllByPanelIdAndIsSent(panelId, isSent, pageRequest);
        PageInfo pageInfo = getPageInfo(pageNum, pageSize, responses);

        List<Response> responseList = new ArrayList<>();
        if(responses.hasContent()) {
            responseList = responses.getContent();
        }

        return responseMapper.toResponseMyPageListResponse(type, responseList, pageInfo);
    }


    // [App] MyPage - 응답 사진 변경하기
    /* 설문 progress 2인 경우에만 변경 가능 */
    public Long updateResponseImgUrl(Panel panel, Long responseId, ResponseImgUrlUpdateRequestDTO responseImgUrlUpdateRequestDTO) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> {
                    throw ResponseNotFound.EXCEPTION;
                });

        // 응답한 패널이 아닌데 수정하려는 경우
        if(!Objects.equals(panel.getId(), response.getPanel().getId())) {
            throw ResponseUnauthorized.EXCEPTION;
        }

        // 이미 종료된 설문의 응답을 수정하려는 경우
        if(response.getSurvey().getProgress() > 2) {
            throw SurveyExpired.EXCEPTION;
        }

        response.setImgUrl(responseImgUrlUpdateRequestDTO.getImgUrl());
        return responseRepository.save(response).getId();
    }
}
