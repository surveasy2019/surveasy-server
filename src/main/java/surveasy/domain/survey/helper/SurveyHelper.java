package surveasy.domain.survey.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
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
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.survey.vo.SurveyAppListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;
import surveasy.global.common.dto.PageInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return status.equals(SurveyStatus.DONE);
    }

    public boolean isWaiting(SurveyStatus status) {
        return status.equals(SurveyStatus.CREATED) || status.equals(SurveyStatus.WAITING);
    }


    /* [Web] 홈화면 진행중인 설문 개수 */
    public Long getSurveyTotalCount() {
        return 1311 + surveyRepository.countByStatusInProgressOrDone();
    }


    /* [Web] 설문 주문하기 */
    public Long createSurvey(SurveyServiceDTO surveyServiceDTO) {
        Survey newSurvey = surveyMapper.toEntity(surveyServiceDTO);
        Survey savedSurvey = surveyRepository.save(newSurvey);

        return savedSurvey.getId();
    }


    /* [Web] 마이 페이지 설문 개수 */
    public Long getMyPageSurveyCount(String email, SurveyStatus status) {
        return surveyRepository.countByEmailAndStatus(email, status);
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

    public Survey getAdminSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId).orElseThrow(() -> SurveyNotFound.EXCEPTION);
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

    /* [App] 진행중인 설문 목록 */
    public List<SurveyAppHomeVo> getSurveyAppHomeList(Panel panel) {
        return surveyRepository.findAllSurveyAppHomeVos(panel);
    }


    /* [App] 설문 현재 응답수 업데이트 */
    public Integer updateCurrentHeadCount(Survey survey) {
        Integer responseCount = survey.getResponseCount();
        survey.setResponseCount(responseCount + 1);

        return surveyRepository.save(survey).getResponseCount();
    }


    /* [App] 설문 status 업데이트 (IN_PROGRESS -> DONE) */
    public void updateStatusToDone(Survey survey) {
        survey.setStatus(SurveyStatus.DONE);
        surveyRepository.save(survey);
    }

    public Page<SurveyAppListVo> getSurveyAppList(Panel panel, Pageable pageable) {
        return surveyRepository.findAllSurveyAppListVos(panel, pageable);
    }

    public SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId) {
        return surveyRepository.findSurveyAppListDetailVo(surveyId)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);
    }

    public void deleteAdminSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> SurveyNotFound.EXCEPTION);
        surveyRepository.delete(survey);
    }
}
