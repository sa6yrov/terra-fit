package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.TrainingGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingGroupRepository extends JpaRepository<TrainingGroup, Long> {

}
