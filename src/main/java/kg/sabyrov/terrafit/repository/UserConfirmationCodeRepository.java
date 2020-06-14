package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfirmationCodeRepository extends JpaRepository<UserConfirmationCode, Long> {

}
