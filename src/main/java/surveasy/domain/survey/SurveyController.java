package surveasy.domain.survey;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.dto.request.admin.SurveyAdminDTO;
import surveasy.domain.survey.dto.request.web.SurveyUpdateRequestDto;
import surveasy.domain.survey.dto.request.web.SurveyCreateRequestDto;
import surveasy.domain.survey.dto.response.app.SurveyAppHomeListResponse;
import surveasy.domain.survey.dto.response.app.SurveyAppListResponse;
import surveasy.domain.survey.dto.response.web.*;
import surveasy.domain.survey.service.SurveyService;
import surveasy.domain.survey.vo.SurveyAppListDetailVo;
import surveasy.domain.user.domain.User;
import surveasy.global.common.annotation.CurrentPanel;
import surveasy.global.common.annotation.CurrentUser;

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
    @GetMapping("/count")
    public SurveyHomeResponse getSurveyTotalCount() {
        return surveyService.getSurveyTotalCount();
    }

    @Operation(summary = "Web 설문 주문하기")
    @PostMapping
    public SurveyIdResponse createSurvey(@CurrentUser User user,
                                         @RequestBody @Valid SurveyCreateRequestDto surveyCreateRequestDto) {
        return surveyService.createSurvey(user, surveyCreateRequestDto);
    }

    @Operation(summary = "Web 설문 리스트 불러오기")
    @GetMapping("/list")
    public SurveyListResponse getSurveyList(@PageableDefault(size = 10) Pageable pageable) {
        return surveyService.getSurveyList(pageable);
    }

    @Operation(summary = "Web 마이페이지 진행중 / 완료 설문 개수")
    @GetMapping("/mypage")
    public SurveyMyPageCountResponse getMyPageSurveyCounts(@CurrentUser User user) {
        return surveyService.getMyPageSurveyCounts(user);
    }

    @Operation(summary = "Web 마이페이지 나의 설문 리스트")
    @GetMapping("/mypage/list")
    public SurveyMyPageOrderListResponse getMyPageOrderList(@CurrentUser User user) {
        return surveyService.getSurveyMyPageOrderList(user);
    }

    @Operation(summary = "Web 마이페이지 설문 수정 - title, link, headcount, price")
    @PatchMapping("/{surveyId}")
    public SurveyIdResponse updateSurvey(@PathVariable Long surveyId,
                                         @RequestBody SurveyUpdateRequestDto surveyUpdateRequestDto,
                                         @CurrentUser User user) {
        return surveyService.updateSurvey(surveyId, surveyUpdateRequestDto, user);
    }

    @Operation(summary = "Web 마이페이지 설문 삭제")
    @DeleteMapping("/{surveyId}")
    public SurveyIdResponse deleteSurvey(@PathVariable Long surveyId,
                                         @CurrentUser User user) {
        return surveyService.deleteSurvey(surveyId, user);
    }

    @Operation(summary = "Admin 설문 리스트")
    @GetMapping("/admin")
    public SurveyAdminListResponse getAdminSurveyList(@PageableDefault(size = 10) Pageable pageable,
                                                      @RequestParam(required = false) final String username) {
        return surveyService.getAdminSurveyList(pageable, username);
    }

    @Operation(summary = "Admin 설문 1개 상세 정보")
    @GetMapping("/admin/{surveyId}")
    public ResponseEntity<Survey> getAdminSurvey(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyService.getAdminSurvey(surveyId));
    }

    @Operation(summary = "Admin 설문 정보 업데이트 - status, noticeToPanel, reward, link, dueDate, headCount")
    @PatchMapping("/admin/{surveyId}")
    public SurveyIdResponse updateAdminSurvey(@PathVariable Long surveyId,
                                              @RequestBody SurveyAdminDTO surveyAdminDTO) {
        return surveyService.updateAdminSurvey(surveyId, surveyAdminDTO);
    }

    @Operation(summary = "어드민 설문 삭제")
    @DeleteMapping("/admin/{surveyId}")
    public void deleteAdminSurvey(@PathVariable Long surveyId) {
        surveyService.deleteAdminSurvey(surveyId);
    }

    @Operation(summary = "App 홈화면 설문 리스트")
    @GetMapping("/app/home")
    public SurveyAppHomeListResponse getSurveyAppHomeList(@CurrentPanel Panel panel) {
        return surveyService.getSurveyAppHomeList(panel);
    }

    @Operation(summary = "App 설문 리스트")
    @GetMapping("/app")
    public SurveyAppListResponse getSurveyAppList(@CurrentPanel Panel panel,
                                                  @PageableDefault(size = 10) Pageable pageable) {
        return surveyService.getSurveyAppList(panel, pageable);
    }

    @Operation(summary = "App 설문 1개 상세 정보")
    @GetMapping("/app/{surveyId}")
    public ResponseEntity<SurveyAppListDetailVo> getSurveyAppListDetail(@PathVariable Long surveyId) {
        return ResponseEntity.ok(surveyService.getSurveyAppListDetail(surveyId));
    }
}
