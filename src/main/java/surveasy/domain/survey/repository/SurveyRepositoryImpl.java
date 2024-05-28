package surveasy.domain.survey.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.survey.domain.Survey;
import surveasy.domain.survey.domain.SurveyStatus;
import surveasy.domain.survey.domain.option.SurveyLanguage;
import surveasy.domain.survey.domain.target.TargetAge;
import surveasy.domain.survey.domain.target.TargetGender;
import surveasy.domain.survey.vo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static surveasy.domain.survey.domain.QSurvey.survey;
import static surveasy.domain.response.domain.QResponse.response;

@Repository
@RequiredArgsConstructor
public class SurveyRepositoryImpl implements SurveyRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long findMaxSid() {
        return jpaQueryFactory
                .select(survey.sid.max())
                .from(survey)
                .fetchOne();
    }

    @Override
    public long countByStatusInProgressOrDone() {
        return jpaQueryFactory
                .select(survey.count())
                .from(survey)
                .where(
                        inVisibleStatus()
                )
                .fetchFirst();
    }

    @Override
    public long countByEmailAndStatus(String email, SurveyStatus status) {
        return jpaQueryFactory
                .select(survey.count())
                .from(survey)
                .where(
                        eqEmail(email),
                        eqStatus(status)
                )
                .fetchFirst();
    }

    @Override
    public Page<Survey> findAll(Pageable pageable, String username) {
        List<Survey> surveyList = jpaQueryFactory
                .select(survey)
                .from(survey)
                .where(
                        eqUsername(username)
                )
                .orderBy(survey.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(survey.count())
                .from(survey)
                .where(
                        eqUsername(username)
                );

        return PageableExecutionUtils.getPage(surveyList, pageable, count::fetchOne);
    }

    @Override
    public Page<SurveyListVo> findAllSurveyListVos(Pageable pageable) {
        List<SurveyListVo> surveyListVos;
        JPAQuery<Long> count;

        surveyListVos = jpaQueryFactory
                .select(Projections.constructor(SurveyListVo.class,
                        survey.sid,
                        survey.title,
                        survey.link,
                        survey.status,
                        survey.dueDate,
                        survey.targetInput,
                        survey.headCount,
                        survey.responseCount,
                        survey.username))
                .from(survey)
                .where(
                        inVisibleStatus()
                )
                .orderBy(survey.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        count = jpaQueryFactory.select(survey.count())
                .from(survey)
                .where(
                        inVisibleStatus()
                );

        return PageableExecutionUtils.getPage(surveyListVos, pageable, count::fetchOne);
    }

    @Override
    public List<SurveyMyPageOrderVo> findAllSurveyMyPageVosByEmail(String email) {
        return jpaQueryFactory
                .select(Projections.constructor(SurveyMyPageOrderVo.class,
                        survey.id,
                        survey.sid,
                        survey.title,
                        survey.headCount,
                        survey.responseCount,
                        survey.spendTime,
                        survey.targetAgeListStr,
                        survey.targetGender,
                        survey.status,
                        survey.price,
                        survey.identity,
                        survey.link,
                        survey.uploadedAt,
                        survey.dueDate,
                        survey.reviewId))
                .from(survey)
                .where(
                        eqEmail(email)
                )
                .orderBy(survey.id.desc())
                .fetch();
    }

    @Override
    public List<SurveyAppHomeVo> findAllSurveyAppHomeVos(Panel panel) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekBefore = now.minusDays(8);

        return jpaQueryFactory
                .select(Projections.constructor(SurveyAppHomeVo.class,
                        survey.id,
                        survey.title,
                        survey.targetInput,
                        survey.reward,
                        survey.responseCount))
                .from(survey)
                .leftJoin(response)
                .on(
                        response.survey.eq(survey),
                        response.panel.eq(panel),
                        response.createdAt.between(oneWeekBefore, now))
                .where(
                        response.isNull(),
                        eqStatus(SurveyStatus.IN_PROGRESS),
                        dueDateAfterNow(),
                        inTargetGender(panel.getGender()),
                        inTargetAge(panel.getBirth()),
                        getLanguageBuilder(panel.getEnglish())
                )
                .orderBy(survey.dueDate.asc())
                .limit(3)
                .fetch();
    }

    @Override
    public Page<SurveyAppListVo> findAllSurveyAppListVos(Panel panel, Pageable pageable) {
        List<SurveyAppListVo> surveyAppListVos;
        JPAQuery<Long> count;

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekBefore = now.minusDays(8);

        surveyAppListVos = jpaQueryFactory
                .select(Projections.constructor(SurveyAppListVo.class,
                    survey.id,
                    survey.title,
                    survey.reward,
                    survey.headCount,
                    survey.spendTime,
                    survey.targetInput,
                    survey.status,
                    survey.responseCount,
                    survey.dueDate,
                    response))
                .from(survey)
                .leftJoin(response)
                .on(
                        response.survey.eq(survey),
                        response.panel.eq(panel),
                        response.createdAt.between(oneWeekBefore, now))
                .where(
                        sidGtZero(),
                        inVisibleStatus(),
                        inTargetGender(panel.getGender()),
                        inTargetAge(panel.getBirth()),
                        getLanguageBuilder(panel.getEnglish())
                )
                .orderBy(
                        statusOrder().asc(),
                        overdueOrder(now).asc(),
                        response.id.asc(),
                        survey.dueDate.asc()
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        count = jpaQueryFactory.select(survey.count())
                .from(survey)
                .leftJoin(response)
                .on(
                        response.survey.eq(survey),
                        response.panel.eq(panel),
                        response.createdAt.between(oneWeekBefore, now))
                .where(
                        sidGtZero(),
                        inVisibleStatus(),
                        inTargetGender(panel.getGender()),
                        inTargetAge(panel.getBirth()),
                        getLanguageBuilder(panel.getEnglish())
                );

        return PageableExecutionUtils.getPage(surveyAppListVos, pageable, count::fetchOne);
    }

    @Override
    public Optional<SurveyAppListDetailVo> findSurveyAppListDetailVo(Long surveyId) {
        return Optional.ofNullable(jpaQueryFactory
                .select(Projections.constructor(SurveyAppListDetailVo.class,
                        survey.id,
                        survey.title,
                        survey.reward,
                        survey.headCount,
                        survey.spendTime,
                        survey.responseCount,
                        survey.targetInput,
                        survey.noticeToPanel,
                        survey.link,
                        survey.description))
                .from(survey)
                .where(
                        eqSurveyId(surveyId)
                )
                .fetchFirst());
    }

    private BooleanExpression eqSurveyId(Long surveyId) {
        return surveyId != null ? survey.id.eq(surveyId) : null;
    }

    private BooleanExpression eqEmail(String email) {
        return email != null ? survey.email.eq(email) : null;
    }

    private BooleanExpression eqUsername(String username) {
        return username != null ? survey.username.containsIgnoreCase(username) : null;
    }

    private BooleanExpression eqStatus(SurveyStatus status) {
        return status != null ? survey.status.eq(status) : null;
    }

    private BooleanExpression sidGtZero() {
        return survey.sid.gt(0);
    }

    private BooleanExpression dueDateAfterNow() {
        LocalDateTime now = LocalDateTime.now();
        return survey.dueDate.after(now);
    }

    private BooleanExpression inVisibleStatus() {
        return survey.status.in(SurveyStatus.IN_PROGRESS, SurveyStatus.DONE);
    }

    private BooleanExpression inTargetGender(TargetGender targetGender) {
        return targetGender != null ? survey.targetGender.in(TargetGender.ALL, targetGender) : null;
    }

    private BooleanExpression inTargetAge(LocalDate birth) {
        return birth != null ? survey.targetAgeListStr.eq("ALL").or(survey.targetAgeListStr.contains(TargetAge.from(birth).toString())) : null;
    }

    private BooleanBuilder getLanguageBuilder(Boolean english) {
        if(english)
            return new BooleanBuilder().and(survey.language.in(SurveyLanguage.KOR, SurveyLanguage.ENG));
        else
            return new BooleanBuilder().and(survey.language.eq(SurveyLanguage.KOR));
    }

    private NumberExpression<Integer> overdueOrder(LocalDateTime now) {
        return new CaseBuilder()
                .when(survey.dueDate.after(now)).then(0)
                .otherwise(1);
    }

    private NumberExpression<Integer> statusOrder() {
        return new CaseBuilder()
                .when(survey.status.eq(SurveyStatus.IN_PROGRESS)).then(0)
                .otherwise(1);
    }
}
