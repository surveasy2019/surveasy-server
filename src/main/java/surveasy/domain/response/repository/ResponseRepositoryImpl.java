package surveasy.domain.response.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import surveasy.domain.response.domain.QResponse;
import surveasy.domain.response.domain.Response;

@Repository
@RequiredArgsConstructor
public class ResponseRepositoryImpl implements ResponseRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Response findByPidAndSidAndIsValid(Long panelId, Long surveyId, Boolean isValid) {
        QResponse qResponse = QResponse.response;

        return jpaQueryFactory
                .select(qResponse.response)
                .from(qResponse)
                .where(qResponse.survey.id.eq(surveyId))
                .where(qResponse.panel.id.eq(panelId))
//                .where(qResponse.isValid.eq(isValid))
                .fetchOne();
    }
}
