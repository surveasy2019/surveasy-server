package surveasy.domain.survey.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.exception.SurveyNotFound;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;

@Component
@RequiredArgsConstructor
public class SurveyHelper {

    private final SurveyRepository surveyRepository;
    private final SurveyMapper surveyMapper;

    public Survey findById(Long surveyId) {
        return surveyRepository
                .findById(surveyId)
                .orElseThrow(() -> {
                    throw SurveyNotFound.EXCEPTION;
                });
    }

    public Long createSurvey(SurveyServiceDTO surveyServiceDTO) {
        Survey newSurvey = surveyMapper.toEntity(surveyServiceDTO);
        Survey savedSurvey = surveyRepository.save(newSurvey);

        return savedSurvey.getId();
    }

    public Long updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        Survey survey = surveyRepository
                        .findById(id)
                        .orElseThrow(() -> {
                            throw SurveyNotFound.EXCEPTION;
                        });

        if(surveyAdminDTO.getProgress() != null) {
            survey.setProgress(surveyAdminDTO.getProgress());
            if(surveyAdminDTO.getProgress() == 2) {
                // lastCheckedId 발급
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
}
