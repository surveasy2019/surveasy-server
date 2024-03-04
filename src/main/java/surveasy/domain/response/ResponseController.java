package surveasy.domain.response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseUpdateRequestDTO;
import surveasy.domain.response.dto.response.AdminSurveyResponseListResponse;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.service.FileService;
import surveasy.domain.response.service.ResponseService;
import surveasy.global.config.user.PanelDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
@Tag(name = "Response")
public class ResponseController {

    private final ResponseService responseService;
    private final FileService fileService;

    @Operation(summary = "App 설문 응답 생성하기")
    @PostMapping("/{surveyId}")
    public ResponseIdResponse createResponse(@AuthenticationPrincipal PanelDetails panelDetails,
                                             @PathVariable Long surveyId,
                                             @RequestBody @Valid ResponseCreateRequestDTO responseCreateRequestDTO) {
        return responseService.createResponse(panelDetails, surveyId, responseCreateRequestDTO);
    }

    @Operation(summary = "App 마이페이지 설문 응답 리스트 불러오기")
    @GetMapping("/{type}")
    public ResponseHistoryListResponse getResponseMyPageList(@AuthenticationPrincipal PanelDetails panelDetails,
                                                             @PathVariable String type,
                                                             @PageableDefault(size = 10) Pageable pageable) {
        return responseService.getResponseMyPageList(panelDetails, type, pageable);
    }

    @Operation(summary = "App 마이페이지 설문 응답 이미지 수정하기")
    @PatchMapping("/{responseId}")
    public ResponseIdResponse updateResponseImgUrl(@AuthenticationPrincipal PanelDetails panelDetails,
                                                   @PathVariable Long responseId,
                                                   @RequestBody ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        return responseService.updateResponseImgUrl(panelDetails, responseId, responseUpdateRequestDTO);
    }

    @Operation(summary = "어드민 설문 응답 1건 업데이트")
    @PatchMapping("/admin/{responseId}")
    public ResponseIdResponse updateResponseAdmin(@PathVariable Long responseId,
                                                  @RequestBody ResponseUpdateRequestDTO responseUpdateRequestDTO) {
        return responseService.updateResponseAdmin(responseId, responseUpdateRequestDTO);
    }

    @Operation(summary = "어드민 설문 1개 전체 응답 리스트")
    @GetMapping("/admin/{surveyId}")
    public AdminSurveyResponseListResponse getAdminSurveyResponseList(@PathVariable Long surveyId) {
        return responseService.getAdminSurveyResponseList(surveyId);
    }

    @Operation(summary = "어드민 정산 결과 csv 파일 다운로드")
    @GetMapping("/admin/download/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
        Resource resource = fileService.loadFileAsResource(fileName);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("text/csv; charset=UTF-8"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }

    @Operation(summary = "어드민 송금 후 정산 완료 처리")
    @GetMapping("/admin/done")
    public void doneAggregation() {
        responseService.doneAggregation();
    }

    @Operation(summary = "임시 자동 정산 트리거")
    @GetMapping("/test")
    public void testAggregation() throws Exception {
         responseService.doAggregation();
    }
}
