package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.SurveyServiceResponse;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyHelper surveyHelper;
    private final SurveyMapper surveyMapper;

    @Transactional
    public SurveyServiceResponse createSurvey(SurveyServiceDTO surveyServiceDTO) {
        return surveyMapper.toSurveyServiceResponse(surveyHelper.createSurvey(surveyServiceDTO));
    }
}
