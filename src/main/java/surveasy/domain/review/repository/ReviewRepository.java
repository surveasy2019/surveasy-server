package surveasy.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.review.domain.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
