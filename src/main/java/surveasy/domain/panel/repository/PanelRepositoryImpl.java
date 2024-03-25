package surveasy.domain.panel.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.panel.domain.QPanel;
import surveasy.domain.panel.vo.PanelInfoVo;
import surveasy.domain.survey.domain.target.TargetGender;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PanelRepositoryImpl implements PanelRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long countActivePanel(TargetGender gender, LocalDate aWeekAgo, LocalDate birthStart, LocalDate birthEnd) {
        QPanel qPanel = QPanel.panel;

        return jpaQueryFactory
                .select(qPanel.count())
                .from(qPanel)
                .where(
                        qPanel.gender.eq(gender),
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qPanel.lastParticipatedAt, ConstantImpl.create("yyyy-mm-dd")).goe(String.valueOf(aWeekAgo)),
                        qPanel.birth.between(birthEnd, birthStart)
                )
                .fetchOne();
    }

    @Override
    public Optional<PanelInfoVo> findPanelInfoVo(Long panelId) {
        QPanel qPanel = QPanel.panel;

        return Optional.ofNullable(jpaQueryFactory.select(
                        Projections.constructor(PanelInfoVo.class,
                                qPanel.name,
                                qPanel.birth,
                                qPanel.gender,
                                qPanel.email,
                                qPanel.phoneNumber,
                                qPanel.accountOwner,
                                qPanel.accountType,
                                qPanel.accountNumber,
                                qPanel.english))
                .from(qPanel)
                .where(qPanel.id.eq(panelId))
                .fetchFirst());
    }
}
