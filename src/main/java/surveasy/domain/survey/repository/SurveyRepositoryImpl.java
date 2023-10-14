package surveasy.domain.survey.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.response.domain.QResponse;
import surveasy.domain.survey.domain.QSurvey;
import surveasy.domain.survey.domain.Survey;

import java.util.List;

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

    @Override
    public List<Survey> findSurveyListByProgressEq2() {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .selectFrom(qSurvey)
                .where(qSurvey.progress.eq(2))
                .fetch();
    }

}
