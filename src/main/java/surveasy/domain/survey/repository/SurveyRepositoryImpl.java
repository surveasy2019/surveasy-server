package surveasy.domain.survey.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.survey.domain.QSurvey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.vo.SurveyAppHomeVo;
import surveasy.domain.survey.vo.SurveyListVo;
import surveasy.domain.survey.vo.SurveyMyPageOrderVo;

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
    public Long countByEmailAndStatus(String email, SurveyStatus status) {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(qSurvey.count())
                .from(qSurvey)
                .where(qSurvey.email.eq(email), qSurvey.status.eq(status))
                .fetchFirst();
    }

    @Override
    public List<SurveyAppHomeVo> findAllSurveyAppHomeVos() {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(Projections.constructor(SurveyAppHomeVo.class,
                        qSurvey.id,
                        qSurvey.title,
                        qSurvey.reward,
                        qSurvey.link,
                        qSurvey.noticeToPanel))
                .from(qSurvey)
                .orderBy(qSurvey.dueDate.desc())
                .where(qSurvey.status.eq(SurveyStatus.IN_PROGRESS))
                .fetch();
    }

    @Override
    public List<SurveyListVo> findAllSurveyListVos() {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(Projections.constructor(SurveyListVo.class,
                        qSurvey.sid,
                        qSurvey.title,
                        qSurvey.link,
                        qSurvey.status,
                        qSurvey.dueDate,
                        qSurvey.targetInput,
                        qSurvey.headCount,
                        qSurvey.responseCount,
                        qSurvey.username))
                .from(qSurvey)
                .where(qSurvey.status.ne(SurveyStatus.CREATED), qSurvey.status.ne(SurveyStatus.WAITING))
                .orderBy(qSurvey.id.desc())
                .fetch();
    }

    @Override
    public List<SurveyMyPageOrderVo> findAllSurveyMyPageVosByEmail(String email) {
        QSurvey qSurvey = QSurvey.survey;

        return jpaQueryFactory
                .select(Projections.constructor(SurveyMyPageOrderVo.class,
                        qSurvey.id,
                        qSurvey.sid,
                        qSurvey.title,
                        qSurvey.headCount,
                        qSurvey.responseCount,
                        qSurvey.spendTime,
                        qSurvey.targetAgeListStr,
                        qSurvey.targetGender,
                        qSurvey.status,
                        qSurvey.price,
                        qSurvey.identity,
                        qSurvey.link,
                        qSurvey.uploadedAt,
                        qSurvey.dueDate))
                .from(qSurvey)
                .where(qSurvey.email.eq(email))
                .orderBy(qSurvey.id.desc())
                .fetch();
    }

}
