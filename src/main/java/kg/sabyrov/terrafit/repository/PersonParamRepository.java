package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.PersonParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonParamRepository extends JpaRepository<PersonParam, Long> {
}
