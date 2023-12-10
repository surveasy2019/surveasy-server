package surveasy.domain.response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.response.domain.Response2;
import surveasy.domain.response.dto.request.Response2CreateDTO;
import surveasy.domain.response.dto.request.ResponseCreateRequestDTO;
import surveasy.domain.response.dto.request.ResponseImgUrlUpdateRequestDTO;
import surveasy.domain.response.dto.response.ResponseIdResponse;
import surveasy.domain.response.dto.response.ResponseMyPageListResponse;
import surveasy.domain.response.repository.Response2Repository;
import surveasy.domain.response.service.ResponseService;
import surveasy.domain.response.util.PartitionUtil;
import surveasy.global.config.user.PanelDetails;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/response")
@Tag(name = "Response")
public class ResponseController {

    private final ResponseService responseService;
    private final Response2Repository response2Repository;
    private final PartitionUtil partitionUtil;

    @PostMapping("/2")
    public ResponseEntity<Response2> create2(@RequestBody Response2CreateDTO response2CreateDTO) {
        Response2 response2 = Response2.of(response2CreateDTO);
        return ResponseEntity.ok(response2Repository.save(response2));
    }

    @GetMapping("/2/{id}")
    public ResponseEntity<Response2> get2(@PathVariable Long id) {
        return ResponseEntity.ok(response2Repository.findById(id).orElse(null));
    }

    @GetMapping("/2/current")
    public ResponseEntity<List<Response2>> get2current() {
        return ResponseEntity.ok(response2Repository.getCurrentList());
    }

    @GetMapping("/2/last")
    public ResponseEntity<List<Response2>> get2last() {
        return ResponseEntity.ok(response2Repository.getLastList());
    }

    @GetMapping("2/create")
    public void create() {
        partitionUtil.updatePartition();
    }

    @GetMapping("2/delete/{partitionName}")
    public void delete(@PathVariable String partitionName) {
        partitionUtil.deletePartition(partitionName);
    }

    @Operation(summary = "App 설문 응답 생성하기")
    @PostMapping("/{surveyId}")
    public ResponseIdResponse createResponse(@AuthenticationPrincipal PanelDetails panelDetails,
                                             @PathVariable Long surveyId,
                                             @RequestBody @Valid ResponseCreateRequestDTO responseCreateRequestDTO) {
        return responseService.createResponse(panelDetails, surveyId, responseCreateRequestDTO);
    }

    @Operation(summary = "App 마이페이지 설문 응답 리스트 불러오기")
    @GetMapping("/mypage/list/{type}")
    public ResponseMyPageListResponse getResponseMyPageList(@AuthenticationPrincipal PanelDetails panelDetails,
                                                            @PathVariable String type,
                                                            @PageableDefault(size = 10) Pageable pageable) {
        return responseService.getResponseMyPageList(panelDetails, type, pageable);
    }

    @Operation(summary = "App 마이페이지 설문 응답 이미지 수정하기")
    @PutMapping("/mypage/update/{responseId}")
    public ResponseIdResponse updateResponseImgUrl(@AuthenticationPrincipal PanelDetails panelDetails,
                                                   @PathVariable Long responseId,
                                                   @RequestBody ResponseImgUrlUpdateRequestDTO responseImgUrlUpdateRequestDTO) {
        return responseService.updateResponseImgUrl(panelDetails, responseId, responseImgUrlUpdateRequestDTO);
    }
}
