package surveasy.domain.survey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEditDTO;
import surveasy.domain.survey.dto.request.web.SurveyMyPageEmailDTO;
import surveasy.domain.survey.dto.request.web.SurveyServiceDTO;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.service.SurveyService;
import surveasy.global.config.user.PanelDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/survey")
@Tag(name = "Survey")
public class SurveyController {

    private final SurveyService surveyService;

    @Operation(summary = "AWS Target Group 확인용")
    @GetMapping("/tg")
    public ResponseEntity<String> tg() {
        return ResponseEntity.ok("success");
    }

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

    @Operation(summary = "Web 마이페이지 진행중 / 완료 설문 개수")
    @PostMapping("/mypage")
    public SurveyMyPageCountResponse getMyPageSurveyCounts(@RequestBody @Valid SurveyMyPageEmailDTO surveyMyPageEmailDTO) {
        return surveyService.getMyPageSurveyCounts(surveyMyPageEmailDTO.getEmail());
    }

    @Operation(summary = "Web 마이페이지 나의 설문 리스트")
    @PostMapping("/mypage/list")
    public SurveyMyPageOrderListResponse getMyPageOrderList(@RequestBody @Valid SurveyMyPageEmailDTO surveyMyPageEmailDTO) {
        return surveyService.getSurveyMyPageOrderList(surveyMyPageEmailDTO.getEmail());
    }

    @Operation(summary = "Web 마이페이지 설문 수정 - title, link, headCount, price")
    @PatchMapping("/mypage/edit/{surveyId}")
    public SurveyIdResponse editMyPageSurvey(@PathVariable Long surveyId, @RequestBody SurveyMyPageEditDTO surveyMyPageEditDTO) {
        return surveyService.editMyPageSurvey(surveyId, surveyMyPageEditDTO);
    }

    @Operation(summary = "Web 마이페이지 설문 삭제")
    @DeleteMapping("/mypage/delete/{surveyId}")
    public SurveyIdResponse deleteMyPageSurvey(@PathVariable Long surveyId) {
        return surveyService.deleteMyPageSurvey(surveyId);
    }

    @Operation(summary = "App 홈화면 설문 리스트")
    @GetMapping("/app/home")
    public SurveyAppHomeListResponse getAppSurveyHomeList(@AuthenticationPrincipal PanelDetails panelDetails) {
        return surveyService.getSurveyAppHomeListResponse(panelDetails);
    }

    @Operation(summary = "Admin 설문 리스트")
    @GetMapping("/admin/list")
    public SurveyAdminListResponse getAdminSurveyList(@PageableDefault(size = 10) Pageable pageable) {
        return surveyService.getAdminSurveyList(pageable);
    }

    @Operation(summary = "Admin 설문 정보 업데이트 - progress, link, noticeToPanel, panelReward")
    @PatchMapping("/admin/{surveyId}")
    public SurveyIdResponse updateAdminSurvey(@PathVariable Long surveyId, @RequestBody SurveyAdminDTO surveyAdminDTO) {
        return surveyService.updateAdminSurvey(surveyId, surveyAdminDTO);
    }
}
