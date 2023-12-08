package surveasy.domain.survey.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.web.SurveyAdminListResponse;
import surveasy.domain.survey.exception.SurveyCannotDelete;
import surveasy.domain.survey.exception.SurveyCannotEdit;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.domain.survey.vo.SurveyAppHomeVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;
import surveasy.global.common.dto.PageInfo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SurveyHelper {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    public PageInfo getPageInfo(int pageNum, int pageSize, Page<Survey> surveys) {
        return PageInfo.builder()
                .pageNum(pageNum)
                .pageSize(pageSize)
                .totalElements(surveys.getTotalElements())
                .totalPages(surveys.getTotalPages())
                .build();
    }

    public boolean isDone(SurveyStatus status) {
        return status.equals(SurveyStatus.DONE) || status.equals(SurveyStatus.REVIEW_DONE);
    }

    private boolean isWaiting(SurveyStatus status) {
        return status.equals(SurveyStatus.CREATED) || status.equals(SurveyStatus.WAITING);
    }


    /* [Web] 홈화면 진행중인 설문 개수 */
    public Long getSurveyTotalCount() {
        return surveyRepository.countByStatus(SurveyStatus.IN_PROGRESS);
    }


    /* [Web] 설문 주문하기 */
    public Long createSurvey(SurveyServiceDTO surveyServiceDTO) {
        Survey newSurvey = surveyMapper.toEntity(surveyServiceDTO);
        Survey savedSurvey = surveyRepository.save(newSurvey);

        return savedSurvey.getId();
    }


    /* [Web] 마이 페이지 진행중인 설문 개수 */
    public Long getMyPageSurveyOngoingCount(String email) {
        return surveyRepository.countByEmailAndStatus(email, "ongoing");
    }


    /* [Web] 마이 페이지 완료된 설문 개수 */
    public Long getMyPageSurveyDoneCount(String email) {
        return surveyRepository.countByEmailAndStatus(email, "done");
    }


    /* [Web] 마이 페이지 주문 목록 */
    public List<SurveyMyPageOrderVo> getMyPageOrderList(String email) {
        return surveyRepository.findAllSurveyMyPageVosByEmail(email);
    }


    /* [Web] 마이 페이지 설문 수정 (title, link, headCount, price)
    * progress 2 미만일 경우만 가능 */
    public Long editMyPageSurvey(Long id, SurveyMyPageEditDTO surveyMyPageEditDTO) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);

        if(!isWaiting(survey.getStatus())) {
            throw SurveyCannotEdit.EXCEPTION;
        }

        if(surveyMyPageEditDTO.getTitle() != null) {
            survey.setTitle(surveyMyPageEditDTO.getTitle());
        }

        if(surveyMyPageEditDTO.getLink() != null) {
            survey.setLink(surveyMyPageEditDTO.getLink());
        }

        if(surveyMyPageEditDTO.getHeadCount() != null) {
            survey.setHeadCount(surveyMyPageEditDTO.getHeadCount());
        }

        if(surveyMyPageEditDTO.getPrice() != null) {
            survey.setPrice(surveyMyPageEditDTO.getPrice());
        }

        return surveyRepository.save(survey).getId();
    }


    /* [Web] 마이 페이지 설문 삭제
    * progress 2 미만일 경우만 가능 */
    public Long deleteMyPageSurvey(Long id) {
        Survey survey = surveyRepository.findById(id)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);

        if(!isWaiting(survey.getStatus())) {
            throw SurveyCannotDelete.EXCEPTION;
        }

        surveyRepository.deleteById(id);
        return id;
    }


    /* [Web] 어드민 설문 전체 목록 */
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable) {
        int pageNum = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        PageRequest pageRequest = PageRequest.of(pageNum, pageSize, Sort.by("id").descending());
        Page<Survey> surveys = surveyRepository.findAll(pageRequest);
        PageInfo pageInfo = getPageInfo(pageNum, pageSize, surveys);

        List<Survey> surveyList = new ArrayList<>();
        if(surveys.hasContent()) {
            surveyList = surveys.getContent();
        }

        return SurveyAdminListResponse.of(surveyList, pageInfo);
    }


    /* [Web] 현재 sid 최댓값 가져오기
    * 검수 후 progress == 2로 업데이트 하려는 경우, 현재 sid == 0이면 sid를 (최댓값 + 1)으로 부여 */
    private Long findMaxSid() {
        return surveyRepository.findMaxSid();
    }


    /* [Web] 어드민 검수 완료 (progress, noticeToPanel, reward)
    * sid == 0 인 경우, sid를 current max 값으로 업데이트 */
    public Long updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        Survey survey = surveyRepository.findById(id)
                        .orElseThrow(() -> SurveyNotFound.EXCEPTION);

        if(surveyAdminDTO.getStatus() != null) {
            survey.setStatus(surveyAdminDTO.getStatus());

            if(survey.getSid() == 0 && surveyAdminDTO.getStatus().equals(SurveyStatus.IN_PROGRESS)) {
                survey.setSid(findMaxSid()+1);      // sid 발급
            }
        }

        if(surveyAdminDTO.getReward() != null) {
            survey.setReward(surveyAdminDTO.getReward());
        }

        if(surveyAdminDTO.getLink() != null) {
            survey.setLink(surveyAdminDTO.getLink());
        }

        if(surveyAdminDTO.getNoticeToPanel() != null) {
            survey.setNoticeToPanel(surveyAdminDTO.getNoticeToPanel());
        }

        return surveyRepository.save(survey).getId();
    }

    /* [App] 진행중인 설문 목록
    * progress == 2 */
    public List<SurveyAppHomeVo> getSurveyListProgressEq2(Long panelId) {
        List<SurveyAppHomeVo> surveyList = surveyRepository.findAllSurveyAppHomeVos();
        return surveyList;
    }


    /* [App] 설문 현재 응답수 업데이트 */
    public Integer updateCurrentHeadCount(Survey survey) {
        Integer responseCount = survey.getResponseCount();
        survey.setResponseCount(responseCount + 1);

        return surveyRepository.save(survey).getResponseCount();
    }


    /* [App] 설문 status 업데이트 (IN_PROGRESS -> DONE) */
    public Long updateStatusToDone(Survey survey) {
        survey.setStatus(SurveyStatus.DONE);
        return surveyRepository.save(survey).getId();
    }
}
