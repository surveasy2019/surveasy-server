package surveasy.domain.survey.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.response.domain.QResponse;
import surveasy.domain.survey.domain.QSurvey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.domain.survey.vo.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    public Page<SurveyListVo> findAllSurveyListVos(Pageable pageable) {
        QSurvey qSurvey = QSurvey.survey;
        List<SurveyListVo> surveyListVos;
        JPAQuery<Long> count;

        surveyListVos = jpaQueryFactory
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
                .where(qSurvey.status.in(SurveyStatus.IN_PROGRESS, SurveyStatus.DONE))
                .orderBy(qSurvey.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        count = jpaQueryFactory.select(qSurvey.count())
                .from(qSurvey)
                .where(qSurvey.status.in(SurveyStatus.IN_PROGRESS, SurveyStatus.DONE));

        return PageableExecutionUtils.getPage(surveyListVos, pageable, count::fetchOne);
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

    @Override
    public List<SurveyAppHomeVo> findAllSurveyAppHomeVos(Panel panel) {
        QSurvey qSurvey = QSurvey.survey;
        QResponse qResponse = QResponse.response;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekBefore = now.minusDays(8);

        return jpaQueryFactory
                .select(Projections.constructor(SurveyAppHomeVo.class,
                        qSurvey.id,
                        qSurvey.title,
                        qSurvey.targetInput,
                        qSurvey.reward,
                        qSurvey.responseCount))
                .from(qSurvey)
                .leftJoin(qResponse)
                .on(
                        qResponse.survey.eq(qSurvey),
                        qResponse.panel.eq(panel),
                        qResponse.createdAt.between(oneWeekBefore, now))
                .where(
                        qResponse.isNull(),
                        qSurvey.status.eq(SurveyStatus.IN_PROGRESS),
                        qSurvey.targetGender.in(TargetGender.ALL, panel.getGender()),
                        qSurvey.targetAgeListStr.eq("ALL")
                                .or(qSurvey.targetAgeListStr.contains(TargetAge.from(panel.getBirth()).toString())))
                .orderBy(qSurvey.dueDate.asc())
                .limit(3)
                .fetch();
    }

    @Override
    public Page<SurveyAppListVo> findAllSurveyAppListVos(Panel panel, Pageable pageable) {
        QSurvey qSurvey = QSurvey.survey;
        QResponse qResponse = QResponse.response;

        List<SurveyAppListVo> surveyAppListVos;
        JPAQuery<Long> count;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekBefore = now.minusDays(8);

        surveyAppListVos = jpaQueryFactory
                .select(Projections.constructor(SurveyAppListVo.class,
                    qSurvey.id,
                    qSurvey.title,
                    qSurvey.reward,
                    qSurvey.headCount,
                    qSurvey.spendTime,
                    qSurvey.targetInput,
                    qSurvey.status,
                    qSurvey.responseCount,
                    qResponse))
                .from(qSurvey)
                .leftJoin(qResponse)
                .on(
                        qResponse.survey.eq(qSurvey),
                        qResponse.panel.eq(panel),
                        qResponse.createdAt.between(oneWeekBefore, now))
                .where(
                        qSurvey.status.eq(SurveyStatus.IN_PROGRESS),
                        qSurvey.targetGender.in(TargetGender.ALL, panel.getGender()),
                        qSurvey.targetAgeListStr.eq("ALL")
                                .or(qSurvey.targetAgeListStr.contains(TargetAge.from(panel.getBirth()).toString())))
                .orderBy(qSurvey.dueDate.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        count = jpaQueryFactory.select(qSurvey.count())
                .from(qSurvey)
                .leftJoin(qResponse)
                .on(
                        qResponse.survey.eq(qSurvey),
                        qResponse.panel.eq(panel),
                        qResponse.createdAt.between(oneWeekBefore, now))
                .where(
                        qSurvey.status.eq(SurveyStatus.IN_PROGRESS),
                        qSurvey.targetGender.in(TargetGender.ALL, panel.getGender()),
                        qSurvey.targetAgeListStr.eq("ALL")
                                .or(qSurvey.targetAgeListStr.contains(TargetAge.from(panel.getBirth()).toString())))
                .where(qSurvey.status.in(SurveyStatus.IN_PROGRESS, SurveyStatus.DONE));

        return PageableExecutionUtils.getPage(surveyAppListVos, pageable, count::fetchOne);
    }

    @Override
    public Optional<SurveyAppListDetailVo> findSurveyAppListDetailVo(Long surveyId) {
        QSurvey qSurvey = QSurvey.survey;

        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(SurveyAppListDetailVo.class,
                        qSurvey.id,
                        qSurvey.title,
                        qSurvey.reward,
                        qSurvey.headCount,
                        qSurvey.spendTime,
                        qSurvey.responseCount,
                        qSurvey.targetInput,
                        qSurvey.noticeToPanel,
                        qSurvey.link))
                .from(qSurvey)
                .where(qSurvey.id.eq(surveyId))
                .fetchFirst());
    }
}
