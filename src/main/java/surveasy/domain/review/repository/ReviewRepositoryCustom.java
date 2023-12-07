package surveasy.domain.review.repository;

import surveasy.domain.review.vo.HomeReviewVo;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<HomeReviewVo> findAllHomeReviewVo();


}
