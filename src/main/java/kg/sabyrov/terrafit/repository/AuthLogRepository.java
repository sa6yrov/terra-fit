package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.AuthLog;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthLogRepository extends JpaRepository<AuthLog, Long> {
    Integer countAllByStatusAndUser(Status status, User user);

}
