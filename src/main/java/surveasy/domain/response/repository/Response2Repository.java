package surveasy.domain.response.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import surveasy.domain.response.domain.Response2;

import java.sql.Timestamp;
import java.util.List;

public interface Response2Repository extends JpaRepository<Response2, Long> {

    @Query(value = "SELECT * FROM response2 PARTITION (pCurrent)", nativeQuery = true)
    List<Response2> getCurrentList();

    @Query(value = "SELECT * FROM response2 PARTITION (pLast)", nativeQuery = true)
    List<Response2> getLastList();

//    @Query(nativeQuery = true,
//            value = "ALTER TABLE response2 " +
//                    "REORGANIZE PARTITION pCurrent into PARTITION pNow VALUES LESS THAN (unix_timestamp(:current_timestamp))" +
//                    "PARTITION pCurrent VALUES LESS THAN MAXVALUE)")
//    void updatePartition(@Param("current_timestamp") Timestamp currentTimestamp);
}
