package surveasy.domain.survey.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.survey.domain.QSurvey;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Long findMaxSid() {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(qSurvey.sid.max())
                .from(qSurvey)
                .fetchOne();
    }

}
