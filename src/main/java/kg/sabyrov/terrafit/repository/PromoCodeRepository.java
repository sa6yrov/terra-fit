package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.PromoCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoCodeRepository extends JpaRepository<PromoCode, Long> {
    PromoCode findByName(String promoCode);
}
