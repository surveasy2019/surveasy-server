package surveasy.domain.response.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.helper.PanelHelper;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.exception.ResponseDuplicateData;
import surveasy.domain.response.mapper.ResponseMapper;
import surveasy.domain.response.repository.ResponseRepository;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.exception.SurveyExpired;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.exception.SurveyNotReleased;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.repository.SurveyRepository;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResponseHelper {

    private final ResponseRepository responseRepository;
    private final SurveyRepository surveyRepository;

    private final SurveyHelper surveyHelper;

    private final PanelHelper panelHelper;
    private final ResponseMapper responseMapper;

    Integer[] headCountValue = new Integer[]{30, 40, 50, 60, 70, 80, 90, 100, 120, 140, 160, 180, 200};

    private boolean checkAlreadyExists(Long panelId, Long surveyId) {
        Response response = responseRepository.findByPidAndSidAndIsValid(panelId, surveyId, true);

        if(response != null) return true;
        return false;
    }

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
        if(afterResponseCount > headCountValue[survey.getHeadCount()] + 5) {
            surveyHelper.updateProgress2To3(survey);
        }

        /* 패널 정보 업데이트 (rewardCurrent, lastParticipatedDate) */
        panelHelper.updatePanelInfoAfterResponse(panel, survey.getReward());

        return savedResponse.getId();
    }
}
