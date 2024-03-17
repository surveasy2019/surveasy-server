package surveasy.domain.file.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import surveasy.domain.file.domain.File;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {

    Optional<File> findById(Long fileId);

    Page<File> findAllBy(Pageable pageable);

}
