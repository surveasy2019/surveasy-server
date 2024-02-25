package surveasy.domain.response.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import surveasy.domain.response.domain.QResponse;
import surveasy.domain.response.domain.ResponseStatus;
import surveasy.domain.response.vo.ResponseHistoryVo;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ResponseRepositoryImpl implements ResponseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<ResponseHistoryVo> findByPanelIdAndStatusType(Long panelId, String statusType, Pageable pageable) {
        QResponse qResponse = QResponse.response;
        List<ResponseHistoryVo> responseHistoryVos;
        JPAQuery<Long> count;

        if(statusType.equals("before")) {
            responseHistoryVos = jpaQueryFactory.select(Projections.constructor(ResponseHistoryVo.class,
                            qResponse.id,
                            qResponse.survey.title,
                            qResponse.survey.reward,
                            qResponse.imgUrl,
                            qResponse.createdAt,
                            qResponse.sentAt))
                    .from(qResponse)
                    .where(qResponse.panel.id.eq(panelId))
                    .where(qResponse.status.eq(ResponseStatus.CREATED).or(qResponse.status.eq(ResponseStatus.WAITING)).or(qResponse.status.eq(ResponseStatus.UPDATED)))
                    .orderBy(qResponse.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            count = jpaQueryFactory.select(qResponse.count())
                    .from(qResponse)
                    .where(qResponse.panel.id.eq(panelId))
                    .where(qResponse.status.eq(ResponseStatus.CREATED).or(qResponse.status.eq(ResponseStatus.WAITING)).or(qResponse.status.eq(ResponseStatus.UPDATED)));
        }

        else {
            responseHistoryVos =  jpaQueryFactory.select(Projections.constructor(ResponseHistoryVo.class,
                            qResponse.id,
                            qResponse.survey.title,
                            qResponse.survey.reward,
                            qResponse.imgUrl,
                            qResponse.createdAt,
                            qResponse.sentAt))
                    .from(qResponse)
                    .where(qResponse.panel.id.eq(panelId))
                    .where(qResponse.status.eq(ResponseStatus.DONE))
                    .orderBy(qResponse.id.desc())
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            count = jpaQueryFactory.select(qResponse.count())
                    .from(qResponse)
                    .where(qResponse.panel.id.eq(panelId))
                    .where(qResponse.status.eq(ResponseStatus.DONE));
        }

        return PageableExecutionUtils.getPage(responseHistoryVos, pageable, count::fetchOne);
    }
}
