package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.VisitHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VisitHistoryRepository extends JpaRepository<VisitHistory, Long> {

    List<VisitHistory> findAllByVisitTimeBetween(LocalDateTime createdDate, LocalDateTime createdDate2);

    List<VisitHistory> findAllByVisit_Subscription(Subscription subscription);

    List<VisitHistory> findAllByVisitTimeBetweenAndVisit_Subscription_TrainingGroup(LocalDateTime createdDate, LocalDateTime createdDate2, TrainingGroup trainingGroup);
}
