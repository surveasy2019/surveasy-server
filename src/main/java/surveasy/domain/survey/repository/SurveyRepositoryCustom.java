package surveasy.domain.survey.repository;

import surveasy.domain.survey.domain.Survey;

import java.util.List;

public interface SurveyRepositoryCustom {

    Long findMaxSid();

    List<Survey> findSurveyListByProgressEq2();
}
