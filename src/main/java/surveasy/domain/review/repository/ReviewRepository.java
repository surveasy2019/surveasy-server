package surveasy.domain.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.review.domain.Review;
import surveasy.domain.review.vo.ReviewVo;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    Page<ReviewVo> findAllByOrderById(Pageable pageable);
}
