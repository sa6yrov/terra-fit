package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserConfirmationCodeRepository extends JpaRepository<UserConfirmationCode, Long> {

    @Query(value = "select * from user_confirmation_code where user_id = :user_id and is_active = :is_active", nativeQuery = true)
    UserConfirmationCode findByUserAndActive(@Param("user_id") Long id,@Param("is_active") boolean isActive);

}
