package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthLogRepository extends JpaRepository<AuthLog, Long> {
//    Integer countAllByStatusAndUserAndRecovery(Status status, User user, boolean isRecovery);

//    @Query(value = "select count(*) from auth_logs a where a.status = :status and a.user_id = :user_id and a.is_recovery = :is_recovery", nativeQuery = true)
//    Integer countByStatusAndUser(@Param("status") Status status, @Param("user_id") Long userId, @Param("is_recovery") boolean isRecovery);

    @Query("select count (a.id) from AuthLog a where a.status = ?1 and a.user = ?2 and a.isRecovered = ?3")
    Integer getCount(Status status, User user, Integer isRecovered);

}
