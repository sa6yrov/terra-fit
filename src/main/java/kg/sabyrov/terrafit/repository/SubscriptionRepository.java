package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findAllByUser(User user);

    @Query("select sum(s.totalAmount) from Subscription s where s.createdDate between ?1 and ?2")
    BigDecimal getSumByTwoDate(LocalDateTime startDate, LocalDateTime endDate);

    @Query("select sum(s.totalAmount) from Subscription s where s.createdDate between ?1 and ?2 and s.trainingGroup = ?3")
    BigDecimal getSumByTwoDateAndGroup(LocalDateTime startDate, LocalDateTime endDate, TrainingGroup trainingGroup);
}
