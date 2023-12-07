package surveasy.domain.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.review.domain.QReview;
import surveasy.domain.review.domain.ReviewStatus;
import surveasy.domain.review.vo.HomeReviewVo;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<HomeReviewVo> findAllHomeReviewVo() {
        QReview qReview = QReview.review;

        return jpaQueryFactory
                .select(Projections.constructor(HomeReviewVo.class,
                        qReview.id,
                        qReview.survey.title,
                        qReview.username,
                        qReview.grade,
                        qReview.content,
                        qReview.createdAt
                ))
                .from(qReview)
                .where(qReview.status.eq(ReviewStatus.VISIBLE))
                .orderBy(qReview.id.desc())
                .fetch();
    }
}
