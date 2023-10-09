package surveasy.domain.response.repository;

import surveasy.domain.response.domain.Response;

public interface ResponseRepositoryCustom {

    Response findByPidAndSidAndIsValid(Long panelId, Long surveyId, Boolean isValid);
}
