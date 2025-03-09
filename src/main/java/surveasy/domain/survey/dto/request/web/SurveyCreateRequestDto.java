package surveasy.domain.survey.dto.request.web;

import jakarta.validation.constraints.NotNull;
import surveasy.domain.payment.dto.request.PaymentCreateRequestDto;
import surveasy.domain.survey.domain.option.SurveyHeadcount;
import surveasy.domain.survey.domain.option.SurveyIdentity;
import surveasy.domain.survey.domain.option.SurveyLanguage;
import surveasy.domain.survey.domain.option.SurveySpendTime;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDateTime;
import java.util.List;

public record SurveyCreateRequestDto(
        @NotNull SurveyHeadcount headCount,
        @NotNull SurveySpendTime spendTime,
        @NotNull LocalDateTime dueDate,
        @NotNull TargetGender targetGender,
        @NotNull List<TargetAge> targetAgeList,
        @NotNull SurveyLanguage language,
        @NotNull SurveyIdentity identity,
        @NotNull String title,
        String targetInput,
        @NotNull String institute,
        @NotNull String link,
        @NotNull String description,
        String notice,
        @NotNull PaymentCreateRequestDto paymentInfo
) {
}
