package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByCreatedDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2);
}
