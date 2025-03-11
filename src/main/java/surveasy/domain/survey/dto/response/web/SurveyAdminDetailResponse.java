package surveasy.domain.survey.dto.response.web;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.user.vo.FirebaseUserVo;

@Builder(access = AccessLevel.PRIVATE)
public record SurveyAdminDetailResponse(
        Survey survey,
        FirebaseUserVo userInfo
) {

    public static SurveyAdminDetailResponse of(Survey survey, FirebaseUserVo userInfo) {
        return SurveyAdminDetailResponse.builder()
                .survey(survey)
                .userInfo(userInfo)
                .build();
    }
}
