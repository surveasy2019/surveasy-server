package surveasy.Survey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.repository.SurveyRepository;
import surveasy.domain.survey.service.SurveyService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class SurveyServiceTest {

    @Autowired
    SurveyService surveyService;
    @Autowired
    SurveyRepository surveyRepository;

    @Test
    @DisplayName("설문 주문 추가하기")
    public void createSurvey() throws Exception {
        // given
        SurveyCreateRequestDto surveyCreateRequestDto = null;
//        SurveyCreateRequestDto surveyCreateRequestDto = SurveyCreateRequestDto.builder()
//                .english(false)
//                .accountName("계좌주")
//                .dueDate(new Date())
//                .institute("기관기관")
//                .link("linklink")
//                .notice("안내사항")
//                .pointAdd(100)
//                .price(15000)
//                .priceIdentity(1)
//                .headCount(100)
//                .spendTime(1)
//                .tarInput("스마트폰 이용자")
//                .targetingAge(0)
//                .targetingAgeOption(2)
//                .targetingAgeOptionList("20대,30대")
//                .targetingGender(2)
//                .targetingGenderOptionList("남,여")
//                .title("스마트폰에 대한 연구")
//                .build();

        // when
        surveyService.createSurvey(surveyCreateRequestDto);
        Survey findSurvey = surveyRepository.findById(1L).orElseThrow();

        // then
        assertThat(findSurvey.getTitle()).isEqualTo("스마트폰에 대한 연구");

    }

}
