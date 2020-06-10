package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.PersonParam;
import kg.sabyrov.terrafit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonParamRepository extends JpaRepository<PersonParam, Long> {
    List<PersonParam> findAllByUser(User user);
}
