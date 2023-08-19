package surveasy.domain.survey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.SurveyListResponse;
import surveasy.domain.survey.dto.response.SurveyServiceResponse;
import surveasy.domain.survey.service.SurveyService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
@Tag(name = "Survey")
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "설문 주문하기")
    @PostMapping("/service")
    public SurveyServiceResponse createSurvey(@RequestBody @Valid SurveyServiceDTO surveyServiceDTO) {
        return surveyService.createSurvey(surveyServiceDTO);
    }

    @Operation(summary = "설문 리스트 불러오기")
    @GetMapping("/list")
    public SurveyListResponse getSurveyList() {
        return surveyService.getSurveyList();
    }

    @Operation(summary = "어드민 설문 정보 업데이트 - progress, link, noticeToPanel, panelReward")
    @PatchMapping("/admin/{surveyId}")
    public ResponseEntity<Survey> updateAdminSurvey(@PathVariable Long surveyId, @RequestBody SurveyAdminDTO surveyAdminDTO) {
        return ResponseEntity.ok(surveyService.updateAdminSurvey(surveyId, surveyAdminDTO));
    }
}
