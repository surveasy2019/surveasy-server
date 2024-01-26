package surveasy.domain.response.batch;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.apache.commons.lang3.ClassUtils;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

public class QuerydslPagingItemReader<T> extends AbstractPagingItemReader<T> {

    protected final Map<String, Object> jpaPropertyMap = new HashMap<>();
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected Function<JPAQueryFactory, JPAQuery<T>> queryFunction;     // 람다 표현식 사용하기 위해 추가
    protected boolean transacted = true;

    /* Constructor */
    protected QuerydslPagingItemReader() {
        setName(ClassUtils.getShortClassName(QuerydslPagingItemReader.class));
    }

    /* Constructor with params */
    public QuerydslPagingItemReader(EntityManagerFactory entityManagerFactory,
                                    int pageSize,
                                    Function<JPAQueryFactory, JPAQuery<T>> queryFunction) {
        this();
        this.entityManagerFactory = entityManagerFactory;
        this.queryFunction = queryFunction;
        setPageSize(pageSize);
    }

    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    /*
    * Already Opened ItemReader 확인
    * EntityManager 초기화
    * */
    @Override
    protected void doOpen() throws Exception {
        super.doOpen();
        entityManager = entityManagerFactory.createEntityManager(jpaPropertyMap);
        if(entityManager == null) {
            throw new DataAccessResourceFailureException("Unable to obtain an EntityManager");
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doReadPage() {
        clearIfTransacted();

        JPAQuery<T> query = createQuery()
                .offset(getPage() * getPageSize())
                .limit(getPageSize());

        initResults();
        fetchQuery(query);
    }

    protected void clearIfTransacted() {
        if(transacted) entityManager.clear();
    }

    /* queryFunction 이용하여 Querydsl query 생성 */
    protected JPAQuery<T> createQuery() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        return queryFunction.apply(jpaQueryFactory);
    }

    protected void initResults() {
        if(CollectionUtils.isEmpty(results)) {
            results = new CopyOnWriteArrayList<>();
        } else {
            results.clear();
        }
    }

    protected void fetchQuery(JPAQuery<T> query) {
        if(!transacted) {
            List<T> queryResult = query.fetch();
            for(T entity : queryResult) {
                entityManager.detach(entity);
                results.add(entity);
            }
        } else {
            results.addAll(query.fetch());
        }
    }

    @Override
    protected void jumpToItem(int itemIndex) {

    }

    @Override
    protected void doClose() throws Exception {
        entityManager.close();
        super.doClose();
    }
}
