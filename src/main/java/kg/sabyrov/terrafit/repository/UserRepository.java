package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndIsActive(String email, Integer isActive);

    User findByEmail(String email);

    List<User> findBySurnameAndName(String surname, String name);
}
