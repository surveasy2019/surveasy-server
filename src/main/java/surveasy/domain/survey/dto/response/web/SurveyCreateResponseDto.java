package surveasy.domain.survey.dto.response.web;

import lombok.AccessLevel;
import lombok.Builder;
import surveasy.domain.pg.dto.response.TossPaymentsResponseDto;

@Builder(access = AccessLevel.PRIVATE)
public record SurveyCreateResponseDto(
        TossPaymentsResponseDto tossInfo
) {
    public static SurveyCreateResponseDto of(TossPaymentsResponseDto responseDto) {
        return SurveyCreateResponseDto.builder()
                .tossInfo(responseDto)
                .build();
    }
}
