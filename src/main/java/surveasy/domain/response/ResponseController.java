package surveasy.domain.response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseImgUrlUpdateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseHistoryListResponse;
import surveasy.domain.response.service.ResponseService;
import surveasy.global.config.user.PanelDetails;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
@Tag(name = "Response")
public class ResponseController {

    private final ResponseService responseService;

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
                                                   @RequestBody ResponseImgUrlUpdateRequestDTO responseImgUrlUpdateRequestDTO) {
        return responseService.updateResponseImgUrl(panelDetails, responseId, responseImgUrlUpdateRequestDTO);
    }
}
