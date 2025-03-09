package surveasy.domain.survey.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.vo.*;
import surveasy.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface SurveyRepositoryCustom {

    long findMaxSid();

    long countByStatusInProgressOrDone();

    long countByUserIdAndStatus(Long userId, SurveyStatus status);

    Page<Survey> findAll(Pageable pageable, String username);

    Page<SurveyListVo> findAllSurveyListVos(Pageable pageable);

    List<SurveyMyPageOrderVo> findAllSurveyMyPageVosByUserId(Long userId);

    List<SurveyAppHomeVo> findAllSurveyAppHomeVos(Panel panel);

    Page<SurveyAppListVo> findAllSurveyAppListVos(Panel panel, Pageable pageable);

    Optional<SurveyAppListDetailVo> findSurveyAppListDetailVo(Long surveyId);
}
