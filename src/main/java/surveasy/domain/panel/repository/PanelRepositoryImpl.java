package surveasy.domain.panel.repository;

import com.querydsl.core.types.ConstantImpl;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.panel.domain.QPanel;

import java.text.SimpleDateFormat;
import java.util.Date;

@Repository
@RequiredArgsConstructor
public class PanelRepositoryImpl implements PanelRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public long countActivePanel(Integer gender, Date aWeekAgo, Date birthFrom, Date birthTo) {
        QPanel qPanel = QPanel.panel;

        return jpaQueryFactory
                .select(qPanel.count())
                .from(qPanel)
                .where(
                        qPanel.gender.eq(gender),
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qPanel.lastParticipatedAt, ConstantImpl.create("%Y-%m-%d"))
                                .goe(SIMPLE_DATE_FORMAT.format(aWeekAgo)),
                        Expressions.stringTemplate("DATE_FORMAT({0}, {1})", qPanel.birth, ConstantImpl.create("%Y-%m-%d"))
                                .between(SIMPLE_DATE_FORMAT.format(birthTo), SIMPLE_DATE_FORMAT.format(birthFrom))
                )
                .fetchOne();
    }
}
