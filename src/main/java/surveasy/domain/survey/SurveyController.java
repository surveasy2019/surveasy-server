package surveasy.domain.survey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.survey.dto.request.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.web.SurveyHomeResponse;
import surveasy.domain.survey.dto.response.web.SurveyListResponse;
import surveasy.domain.survey.dto.response.web.SurveyIdResponse;
import surveasy.domain.survey.service.SurveyService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
@Tag(name = "Survey")
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "Web 홈화면 설문 개수")
    @GetMapping("/home")
    public SurveyHomeResponse getSurveyTotalCount() {
        return surveyService.getSurveyTotalCount();
    }

    @Operation(summary = "Web 설문 주문하기")
    @PostMapping("/service")
    public SurveyIdResponse createSurvey(@RequestBody @Valid SurveyServiceDTO surveyServiceDTO) {
        return surveyService.createSurvey(surveyServiceDTO);
    }

    @Operation(summary = "Web 설문 리스트 불러오기")
    @GetMapping("/list")
    public SurveyListResponse getSurveyList() {
        return surveyService.getSurveyList();
    }

    @Operation(summary = "Admin 설문 정보 업데이트 - progress, link, noticeToPanel, panelReward")
    @PatchMapping("/admin/{surveyId}")
    public SurveyIdResponse updateAdminSurvey(@PathVariable Long surveyId, @RequestBody SurveyAdminDTO surveyAdminDTO) {
        return surveyService.updateAdminSurvey(surveyId, surveyAdminDTO);
    }
}
