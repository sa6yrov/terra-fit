package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.TrainingSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSectionRepository extends JpaRepository<TrainingSection, Long> {

}
