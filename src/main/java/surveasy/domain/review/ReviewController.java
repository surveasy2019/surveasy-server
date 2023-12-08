package surveasy.domain.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.dto.request.ReviewUpdateRequestDTO;
import surveasy.domain.review.dto.response.HomeReviewVoListResponse;
import surveasy.domain.review.dto.response.ReviewIdResponse;
import surveasy.domain.review.service.ReviewService;
import surveasy.domain.review.vo.ReviewVo;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
@Tag(name = "Review")
public class ReviewController {

    private final ReviewService reviewService;

    @Operation(summary = "리뷰 1개 생성")
    @PostMapping("/{surveyId}")
    public ReviewIdResponse createReview(
            @PathVariable Long surveyId,
            @RequestBody @Valid ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return reviewService.createReview(surveyId, reviewCreateRequestDTO);
    }

    @Operation(summary = "Web 홈화면 리뷰 목록")
    @GetMapping("/home")
    public HomeReviewVoListResponse getHomeReviewVoList() {
        return reviewService.getHomeReviewVoList();
    }

    @Operation(summary = "리뷰 1개 상세 보기")
    @GetMapping("/{surveyId}")
    public ResponseEntity<ReviewVo> getReviewVoBySurveyId(@PathVariable Long surveyId) {
        return ResponseEntity.ok(reviewService.getReviewVoBySurveyId(surveyId));
    }

    @Operation(summary = "Admin 리뷰 수정")
    @PatchMapping("/admin/{reviewId}")
    public ReviewIdResponse updateReview(
            @PathVariable Long reviewId,
            @RequestBody ReviewUpdateRequestDTO reviewUpdateRequestDTO) {
        return reviewService.updateReview(reviewId, reviewUpdateRequestDTO);
    }
}
