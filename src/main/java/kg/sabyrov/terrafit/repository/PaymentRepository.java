package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.Payment;
import kg.sabyrov.terrafit.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
