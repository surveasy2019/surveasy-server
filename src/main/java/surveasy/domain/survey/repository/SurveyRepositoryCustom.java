package surveasy.domain.survey.repository;

import surveasy.domain.survey.vo.SurveyAppHomeVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;

import java.util.List;

public interface SurveyRepositoryCustom {

    Long findMaxSid();

    Long countByEmailAndStatus(String email, String statusStr);

    List<SurveyListVo> findAllSurveyListVos();

    List<SurveyAppHomeVo> findAllSurveyAppHomeVos();

    List<SurveyMyPageOrderVo> findAllSurveyMyPageVosByEmail(String email);
}
