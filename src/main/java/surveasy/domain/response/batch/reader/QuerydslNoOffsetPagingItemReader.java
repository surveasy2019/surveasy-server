package surveasy.domain.response.batch.reader;

import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import surveasy.domain.response.batch.reader.options.QuerydslNoOffsetOptions;

import java.util.function.Function;

public class QuerydslNoOffsetPagingItemReader<T> extends QuerydslPagingItemReader<T> {

    private QuerydslNoOffsetOptions<T> options;

    private QuerydslNoOffsetPagingItemReader() {
        super();
        setName(ClassUtils.getShortName(QuerydslNoOffsetPagingItemReader.class));
    }

    public QuerydslNoOffsetPagingItemReader(EntityManagerFactory entityManagerFactory,
                                            int pageSize,
                                            QuerydslNoOffsetOptions<T> options,
                                            Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        super(entityManagerFactory, pageSize, queryFunction);
        setName(ClassUtils.getShortName(QuerydslNoOffsetPagingItemReader.class));
        this.options = options;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doReadPage() {
        EntityTransaction entityTransaction = getEntityTransactionOrNull();
        JPQLQuery<T> query = createQuery().limit(getPageSize());
        initResults();
        fetchQuery(query, entityTransaction);
        resetCurrentIdIfNotLastPage();
    }

    @Override
    protected JPAQuery<T> createQuery() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        JPAQuery<T> query = queryFunction.apply(jpaQueryFactory);
        options.initKeys(query, getPage());

        return options.createQuery(query, getPage());
    }

    private void resetCurrentIdIfNotLastPage() {
        if(isNotEmptyResults()) {
            options.resetCurrentId(getLastItem());
        }
    }

    private boolean isNotEmptyResults() {
        return !CollectionUtils.isEmpty(results) && results.get(0) != null;
    }

    private T getLastItem() {
        return (T) results.get(results.size() - 1);
    }
}
