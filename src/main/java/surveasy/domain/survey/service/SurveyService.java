package surveasy.domain.survey.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.web.SurveyHomeResponse;
import surveasy.domain.survey.dto.response.web.SurveyListResponse;
import surveasy.domain.survey.dto.response.web.SurveyIdResponse;
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
    public SurveyIdResponse createSurvey(SurveyServiceDTO surveyServiceDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.createSurvey(surveyServiceDTO));
    }

    @Transactional
    public SurveyIdResponse updateAdminSurvey(Long id, SurveyAdminDTO surveyAdminDTO) {
        return surveyMapper.toSurveyIdResponse(surveyHelper.updateAdminSurvey(id, surveyAdminDTO));
    }

    @Transactional
    public SurveyHomeResponse getSurveyTotalCount() {
        return surveyMapper.toSurveyHomeResponse(surveyHelper.getSurveyTotalCount());
    }

    @Transactional
    public SurveyListResponse getSurveyList() {
        List<Survey> surveyList = surveyRepository.findAll();
        return surveyMapper.toSurveyListResponse(surveyList);
    }
}
