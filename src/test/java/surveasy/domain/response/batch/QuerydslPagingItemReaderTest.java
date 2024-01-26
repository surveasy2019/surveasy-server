package surveasy.domain.response.batch;

import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import surveasy.domain.response.domain.Response;
import surveasy.domain.response.domain.ResponseStatus;
import surveasy.domain.response.domain.QResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuerydslPagingItemReader.class)
public class QuerydslPagingItemReaderTest {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Test
    public void reader_test() throws Exception {
        int pageSize = 2;

        QResponse qResponse = QResponse.response;

        QuerydslPagingItemReader<Response> reader = new QuerydslPagingItemReader<>(
                entityManagerFactory,
                pageSize,
                queryFactory -> queryFactory
                        .selectFrom(qResponse)
                        .where(qResponse.status.in(ResponseStatus.CREATED, ResponseStatus.UPDATED))
        );

        reader.open(new ExecutionContext());
        Response r1 = reader.read();
        Response r2 = reader.read();

        System.out.println(r1.getId() + " " + r1.getReward());
        System.out.println(r2.getId() + " " + r2.getReward());

        Assertions.assertEquals(r1.getId(), 3);

    }

}