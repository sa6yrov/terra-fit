package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByCode(String code);
}
