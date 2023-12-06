package surveasy.domain.survey.repository;

import surveasy.domain.survey.vo.SurveyAppHomeListItemVo;
import surveasy.domain.survey.vo.SurveyListItemVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderListItemVo;
import surveasy.domain.survey.vo.SurveyVo;

import java.util.List;

public interface SurveyRepositoryCustom {

    Long findMaxSid();

    List<SurveyListItemVo> findSurveyList();

    List<SurveyAppHomeListItemVo> findSurveyListProgressEq2();

    List<SurveyMyPageOrderListItemVo> findSurveyListByEmail(String email);

    SurveyVo findSurveyVo(Long surveyId);
}
