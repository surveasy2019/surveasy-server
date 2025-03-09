package surveasy.domain.survey.dto.response.web;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;

@Builder(access = AccessLevel.PRIVATE)
public record SurveyRefundResponseDto(
        TossPaymentsResponseDto tossInfo
) {
    public static SurveyRefundResponseDto of(TossPaymentsResponseDto responseDto) {
        return SurveyRefundResponseDto.builder()
                .tossInfo(responseDto)
                .build();
    }
}
