package surveasy.domain.review.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.review.domain.QReview;
import surveasy.domain.review.domain.ReviewStatus;
import surveasy.domain.review.vo.ReviewVo;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<ReviewVo> findAllHomeReviewVo() {
        QReview qReview = QReview.review;

        return jpaQueryFactory
                .select(Projections.constructor(ReviewVo.class,
                        qReview.id,
                        qReview.survey.title,
                        qReview.username,
                        qReview.grade,
                        qReview.content,
                        qReview.status,
                        qReview.createdAt
                ))
                .from(qReview)
                .where(qReview.status.eq(ReviewStatus.VISIBLE))
                .orderBy(qReview.id.desc())
                .fetch();
    }

    @Override
    public Optional<ReviewVo> findReviewVoBySurveyId(Long surveyId) {
        QReview qReview = QReview.review;

        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(ReviewVo.class,
                        qReview.id,
                        qReview.survey.title,
                        qReview.username,
                        qReview.grade,
                        qReview.content,
                        qReview.status,
                        qReview.createdAt
                ))
                .from(qReview)
                .where(qReview.survey.id.eq(surveyId))
                .fetchFirst());
    }
}
