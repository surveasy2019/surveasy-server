package surveasy.domain.survey.repository;

import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.vo.*;

import java.util.List;
import java.util.Optional;

public interface SurveyRepositoryCustom {

    Long findMaxSid();

    Long countByEmailAndStatus(String email, SurveyStatus status);

    List<SurveyListVo> findAllSurveyListVos();

    List<SurveyMyPageOrderVo> findAllSurveyMyPageVosByEmail(String email);

    List<SurveyAppHomeVo> findAllSurveyAppHomeVos(Panel panel);

    List<SurveyAppListVo> findAllSurveyAppListVos(Panel panel);

    Optional<SurveyAppListDetailVo> findSurveyAppListDetailVo(Long surveyId);
}
