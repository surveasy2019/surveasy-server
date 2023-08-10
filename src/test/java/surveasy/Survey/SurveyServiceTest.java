package surveasy.Survey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
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
        SurveyServiceDTO surveyServiceDTO = SurveyServiceDTO.builder()
                .ENTarget(false)
                .account_userName("계좌주")
                .dueDate("2020-12-12")
                .institute("기관기관")
                .link("linklink")
                .notice("안내사항")
                .point_add(100)
                .price(15000)
                .priceIdentity("대학생")
                .requiredHeadCount(100)
                .spendTime("1~3분")
                .target("스마트폰 이용자")
                .targetingAge(0)
                .targetingAgeOption(2)
                .targetingAgeOptionList("20대,30대")
                .targetingGender(2)
                .targetingGenderOptionList("남,여")
                .title("스마트폰에 대한 연구")
                .build();

        // when
        surveyService.createSurvey(surveyServiceDTO);
        Survey findSurvey = surveyRepository.findById(1L).orElseThrow();

        // then
        assertThat(findSurvey.getTitle()).isEqualTo("스마트폰에 대한 연구");

    }

}
