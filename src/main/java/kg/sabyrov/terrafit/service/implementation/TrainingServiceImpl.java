package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.TrainingSection;
import kg.sabyrov.terrafit.repository.TrainingSectionRepository;
import kg.sabyrov.terrafit.service.TrainingSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingSectionService {
   @Autowired
   private TrainingSectionRepository trainingSectionRepository;

    @Override
    public TrainingSection save(TrainingSection trainingSection) {
        return trainingSectionRepository.save(trainingSection);
    }

    @Override
    public TrainingSection getById(Long id) {
        Optional<TrainingSection> trainingSectionOptional = trainingSectionRepository.findById(id);
        return trainingSectionOptional.orElse(null);
    }

    @Override
    public List<TrainingSection> getAll() {
        return trainingSectionRepository.findAll();
    }
}
