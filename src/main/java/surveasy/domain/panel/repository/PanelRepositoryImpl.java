package surveasy.domain.panel.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import surveasy.domain.panel.domain.Panel;
import surveasy.domain.panel.vo.PanelAdminCsvVo;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static surveasy.domain.panel.domain.QPanel.panel;

@Repository
@RequiredArgsConstructor
public class PanelRepositoryImpl implements PanelRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long countActivePanel(TargetGender gender, LocalDate aWeekAgo, LocalDate birthStart, LocalDate birthEnd) {
        return jpaQueryFactory
                .select(panel.count())
                .from(panel)
                .where(
                        panel.gender.eq(gender),
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", panel.lastParticipatedAt, ConstantImpl.create("yyyy-mm-dd")).goe(String.valueOf(aWeekAgo)),
                        panel.birth.between(birthEnd, birthStart)
                )
                .fetchOne();
    }

    @Override
    public Optional<PanelInfoVo> findPanelInfoVo(Long panelId) {
        return Optional.ofNullable(jpaQueryFactory.select(
                Projections.constructor(PanelInfoVo.class,
                        panel.name,
                        panel.birth,
                        panel.gender,
                        panel.email,
                        panel.phoneNumber,
                        panel.accountOwner,
                        panel.accountType,
                        panel.accountNumber,
                        panel.english))
                .from(panel)
                .where(
                        eqPanelId(panelId)
                )
                .fetchFirst());
    }

    @Override
    public List<PanelAdminCsvVo> findAllPanelAdminCsvVos() {
        return jpaQueryFactory
                .select(Projections.constructor(PanelAdminCsvVo.class,
                        panel.id,
                        panel.name,
                        panel.birth,
                        panel.gender,
                        panel.inflowPath,
                        panel.inflowPathEtc,
                        panel.signedUpAt,
                        panel.status))
                .from(panel)
                .fetch();
    }

    @Override
    public Page<Panel> findAll(Pageable pageable, String keyword) {
        List<Panel> panelList = jpaQueryFactory
                .select(panel)
                .from(panel)
                .where(
                        eqKeyword(keyword)
                )
                .orderBy(panel.id.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = jpaQueryFactory
                .select(panel.count())
                .from(panel)
                .where(
                        eqKeyword(keyword)
                );

        return PageableExecutionUtils.getPage(panelList, pageable, count::fetchOne);
    }

    private BooleanExpression eqKeyword(String keyword) {
        return keyword != null ?
                panel.email.containsIgnoreCase(keyword)
                .or(panel.name.containsIgnoreCase(keyword))
                .or(panel.phoneNumber.containsIgnoreCase(keyword)) : null;
    }

    private BooleanExpression eqPanelId(Long panelId) {
        return panelId != null ? panel.id.eq(panelId) : null;
    }
}
