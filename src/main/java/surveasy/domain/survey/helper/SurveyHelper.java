package surveasy.domain.survey.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.dto.request.admin.AdminSurveyUpdateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyUpdateRequestDto;
import surveasy.domain.survey.dto.response.web.SurveyAdminListResponse;
import surveasy.domain.survey.exception.SurveyCannotEdit;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.domain.survey.vo.*;
import surveasy.domain.user.domain.User;

import java.util.List;

@RequiredArgsConstructor
@Component
public class SurveyHelper {

    private final SurveyRepository surveyRepository;

    public boolean isDone(SurveyStatus status) {
        return status.equals(SurveyStatus.DONE);
    }

    public boolean isWaiting(SurveyStatus status) {
        return status.equals(SurveyStatus.CREATED) || status.equals(SurveyStatus.WAITING);
    }

    /* [Web] 홈화면 진행중인 설문 개수 */
    public long getSurveyTotalCount() {
        return 1311 + surveyRepository.countByStatusInProgressOrDone();
    }

    public Page<SurveyListVo> findAllSurveyListVos(Pageable pageable) {
        return surveyRepository.findAllSurveyListVos(pageable);
    }

    /* [Web] 마이 페이지 설문 개수 */
    public Long getCountByUserIdAndStatus(Long userId, SurveyStatus status) {
        return surveyRepository.countByUserIdAndStatus(userId, status);
    }

    /* [Web] 마이 페이지 주문 목록 */
    public List<SurveyMyPageOrderVo> getMyPageOrderList(Long userId) {
        return surveyRepository.findAllSurveyMyPageVosByUserId(userId);
    }


    /* [Web] 마이 페이지 설문 수정 (title, link, headCount, price)
    * progress 2 미만일 경우만 가능 */
    public Long updateSurvey(Survey survey, SurveyUpdateRequestDto surveyUpdateRequestDto) {
        if(!isWaiting(survey.getStatus())) throw SurveyCannotEdit.EXCEPTION;
        survey.updateSurvey(surveyUpdateRequestDto);
        return survey.getId();
    }

    /* [Web] 마이 페이지 설문 삭제
    * progress 2 미만일 경우만 가능 */
    public Long deleteSurvey(Survey survey) {
        surveyRepository.delete(survey);
        return survey.getId();
    }

    /* [Web] 어드민 설문 전체 목록 */
    public SurveyAdminListResponse getAdminSurveyList(Pageable pageable, String username) {
        Page<Survey> surveyList = surveyRepository.findAll(pageable, username);
        return SurveyAdminListResponse.of(surveyList);
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
    public Long updateAdminSurvey(Survey survey, AdminSurveyUpdateRequestDto adminSurveyUpdateRequestDto) {
        if(adminSurveyUpdateRequestDto.status() != null) {
            if(survey.getSid() == 0 && adminSurveyUpdateRequestDto.status().equals(SurveyStatus.IN_PROGRESS)) {
                Long maxSid = findMaxSid();
                survey.updateSurveySid(maxSid+1);      // sid 발급
            }
        }
        survey.updateAdminSurvey(adminSurveyUpdateRequestDto);
        return survey.getId();
    }

    /* [App] 진행중인 설문 목록 */
    public List<SurveyAppHomeVo> getSurveyAppHomeList(Panel panel) {
        return surveyRepository.findAllSurveyAppHomeVos(panel);
    }

    /* [App] 설문 현재 응답수 업데이트 */
    public Integer updateCurrentHeadCount(Survey survey) {
        Integer responseCount = survey.getResponseCount();
        survey.updateSurveyResponseCount(responseCount + 1);
        return survey.getResponseCount();
    }

    /* [App] 설문 status 업데이트 (IN_PROGRESS -> DONE) */
    public void updateStatusToDone(Survey survey) {
        survey.updateSurveyStatus(SurveyStatus.DONE);
    }

    public Page<SurveyAppListVo> getSurveyAppList(Panel panel, Pageable pageable) {
        return surveyRepository.findAllSurveyAppListVos(panel, pageable);
    }

    public SurveyAppListDetailVo getSurveyAppListDetail(Long surveyId) {
        return surveyRepository.findSurveyAppListDetailVo(surveyId)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);
    }

    public Survey findSurveyByIdOrThrow(Long surveyId) {
        return surveyRepository.findById(surveyId)
                .orElseThrow(() -> SurveyNotFound.EXCEPTION);
    }

    public void deleteAdminSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElseThrow(() -> SurveyNotFound.EXCEPTION);
        surveyRepository.delete(survey);
    }

    public Survey createSurveyAndSave(User user, SurveyCreateRequestDto surveyCreateRequestDto) {
        Survey newSurvey = Survey.createSurvey(user, surveyCreateRequestDto);
        return surveyRepository.save(newSurvey);
    }
}
