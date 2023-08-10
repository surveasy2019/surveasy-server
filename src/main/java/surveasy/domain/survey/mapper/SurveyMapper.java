package surveasy.domain.survey.mapper;

import org.springframework.stereotype.Component;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.SurveyServiceResponse;

@Component
public class SurveyMapper {

    // Mapper의 역할 1 : Entity로 매핑
    public Survey toEntity(SurveyServiceDTO surveyServiceDTO) {
        return Survey.of(surveyServiceDTO);
    }

    // Mapper의 역할 2 : DTO로 매핑
    public SurveyServiceResponse toSurveyServiceResponse(Long surveyId) {
        return SurveyServiceResponse.from(surveyId);
    }

}
