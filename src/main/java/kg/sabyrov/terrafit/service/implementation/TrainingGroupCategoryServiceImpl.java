package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.TrainingGroupCategory;
import kg.sabyrov.terrafit.repository.TrainingGroupCategoryRepository;
import kg.sabyrov.terrafit.service.TrainingGroupCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingGroupCategoryServiceImpl implements TrainingGroupCategoryService {
    @Autowired
    private TrainingGroupCategoryRepository trainingGroupCategoryRepository;

    @Override
    public TrainingGroupCategory save(TrainingGroupCategory trainingGroupCategory) {
        return trainingGroupCategoryRepository.save(trainingGroupCategory);
    }

    @Override
    public TrainingGroupCategory getById(Long id) {
        Optional<TrainingGroupCategory> trainingGroupCategory = trainingGroupCategoryRepository.findById(id);
        return trainingGroupCategory.orElse(null);
    }

    @Override
    public List<TrainingGroupCategory> getAll() {
        return trainingGroupCategoryRepository.findAll();
    }
}
