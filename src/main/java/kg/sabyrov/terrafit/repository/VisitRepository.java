package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findAllByCreatedDateBetween(LocalDateTime createdDate, LocalDateTime createdDate2);

    List<Visit> findAllBySubscription(Subscription subscription);

    List<Visit> findAllBySubscription_TrainingGroupAndCreatedDateBetween(TrainingGroup trainingGroup, LocalDateTime createdDate, LocalDateTime createdDate2);
}