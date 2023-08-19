package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.SurveyListResponse;
import surveasy.domain.survey.dto.response.SurveyServiceResponse;
import surveasy.domain.survey.helper.SurveyHelper;
import surveasy.domain.survey.mapper.SurveyMapper;
import surveasy.domain.survey.repository.SurveyRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final SurveyHelper surveyHelper;
    private final SurveyMapper surveyMapper;

    @Transactional
    public SurveyServiceResponse createSurvey(SurveyServiceDTO surveyServiceDTO) {
        return surveyMapper.toSurveyServiceResponse(surveyHelper.createSurvey(surveyServiceDTO));
    }

    @Transactional
    public Survey updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        return surveyHelper.updateAdminSurvey(id, surveyAdminDTO);
    }

    @Transactional
    public SurveyListResponse getSurveyList() {
        List<Survey> surveyList = surveyRepository.findAll();
        return surveyMapper.toSurveyListResponse(surveyList);
    }
}
