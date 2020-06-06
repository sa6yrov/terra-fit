package kg.sabyrov.terrafit.repository;

import kg.sabyrov.terrafit.entity.TrainingGroupCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingGroupCategoryRepository extends JpaRepository<TrainingGroupCategory, Long> {
}
