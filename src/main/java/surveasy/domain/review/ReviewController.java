package surveasy.domain.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import surveasy.domain.review.dto.request.ReviewCreateRequestDTO;
import surveasy.domain.review.dto.response.ReviewIdResponse;
import surveasy.domain.review.service.ReviewService;

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
            @RequestBody ReviewCreateRequestDTO reviewCreateRequestDTO) {
        return reviewService.createReview(surveyId, reviewCreateRequestDTO);
    }
}
