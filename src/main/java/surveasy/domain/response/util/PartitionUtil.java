package surveasy.domain.response.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Repository
public class PartitionUtil {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void updatePartition() {
        long unixNow = Instant.now().getEpochSecond();

        String query = "ALTER TABLE response2 " +
                "REORGANIZE PARTITION pCurrent into (" +
                "PARTITION pTemp VALUES LESS THAN (:now), " +
                "PARTITION pCurrent VALUES LESS THAN (MAXVALUE))";

        entityManager.createNativeQuery(query).setParameter("now", unixNow).executeUpdate();
    }

    @Transactional
    public void deletePartition(String partitionName) {
        String query = "ALTER TABLE response2 DROP PARTITION " + partitionName;
        entityManager.createNativeQuery(query).executeUpdate();
    }
}
