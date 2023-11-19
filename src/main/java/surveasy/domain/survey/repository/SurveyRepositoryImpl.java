package surveasy.domain.survey.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.survey.domain.QSurvey;
import surveasy.domain.survey.vo.SurveyAppHomeListItemVo;
import surveasy.domain.survey.vo.SurveyListItemVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderListItemVo;

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
    public List<SurveyAppHomeListItemVo> findSurveyListProgressEq2() {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(Projections.constructor(SurveyAppHomeListItemVo.class,
                        qSurvey.id,
                        qSurvey.title,
                        qSurvey.reward,
                        qSurvey.link,
                        qSurvey.noticeToPanel))
                .from(qSurvey)
                .orderBy(qSurvey.dueDate.desc())
                .where(qSurvey.progress.eq(2))
                .fetch();
    }

    @Override
    public List<SurveyListItemVo> findSurveyList() {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(Projections.constructor(SurveyListItemVo.class,
                        qSurvey.sid,
                        qSurvey.title,
                        qSurvey.link,
                        qSurvey.progress,
                        qSurvey.dueDate,
                        qSurvey.tarInput,
                        qSurvey.headCount,
                        qSurvey.responseCount,
                        qSurvey.username))
                .from(qSurvey)
                .where(qSurvey.progress.goe(2))
                .orderBy(qSurvey.id.desc())
                .fetch();
    }

    @Override
    public List<SurveyMyPageOrderListItemVo> findSurveyListByEmail(String email) {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(Projections.constructor(SurveyMyPageOrderListItemVo.class,
                        qSurvey.id,
                        qSurvey.sid,
                        qSurvey.title,
                        qSurvey.headCount,
                        qSurvey.responseCount,
                        qSurvey.spendTime,
                        qSurvey.tarAge,
                        qSurvey.tarGender,
                        qSurvey.progress,
                        qSurvey.price,
                        qSurvey.priceIdentity,
                        qSurvey.link,
                        qSurvey.uploadedAt,
                        qSurvey.dueDate))
                .from(qSurvey)
                .where(qSurvey.email.eq(email))
                .orderBy(qSurvey.id.desc())
                .fetch();
    }

}
