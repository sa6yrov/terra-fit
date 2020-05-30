package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionRequestModel;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionResponseModel;
import kg.sabyrov.terrafit.entity.Employee;
import kg.sabyrov.terrafit.entity.TrainingSection;
import kg.sabyrov.terrafit.repository.TrainingSectionRepository;
import kg.sabyrov.terrafit.service.EmployeeService;
import kg.sabyrov.terrafit.service.TrainingSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TrainingServiceImpl implements TrainingSectionService {
    private final TrainingSectionRepository trainingSectionRepository;
    private final EmployeeService employeeService;
    @Autowired
    public TrainingServiceImpl(TrainingSectionRepository trainingSectionRepository, EmployeeService employeeService) {
        this.trainingSectionRepository = trainingSectionRepository;
        this.employeeService = employeeService;
    }

    @Override
    public TrainingSection save(TrainingSection trainingSection) {
        return trainingSectionRepository.save(trainingSection);
    }

    @Override
    public TrainingSectionResponseModel create(TrainingSectionRequestModel trainingSectionRequestModel) {
        TrainingSection trainingSection = saveAndGetTrainingSectionFromDb(trainingSectionRequestModel);

        return TrainingSectionResponseModel.builder()
                .name(trainingSection.getName())
                .coachName(trainingSection.getEmployee().getUser().getName())
                .status(trainingSection.getStatus())
                .build();

    }
    private TrainingSection saveAndGetTrainingSectionFromDb(TrainingSectionRequestModel trainingSectionRequestModel){
        TrainingSection trainingSection = TrainingSection.builder()
                .name(trainingSectionRequestModel.getName())
                .employee(getEmployeeFromDb(trainingSectionRequestModel.getEmployeeEmail()))
                .subscriptionPrice(trainingSectionRequestModel.getSubscriptionPrice())
                .status(trainingSectionRequestModel.getStatus())
                .build();
        return save(trainingSection);
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


    @Override
    public List<TrainingSectionResponseModel> getAllModel() {
        List<TrainingSection> trainingSections = getAll();
        List<TrainingSectionResponseModel> trainingSectionResponseModels = new ArrayList<>();

        for (TrainingSection t : trainingSections) {
            trainingSectionResponseModels.add(TrainingSectionResponseModel.builder()
                    .name(t.getName())
                    .coachName(t.getEmployee().getUser().getName())
                    .subscriptionPrice(t.getSubscriptionPrice())
                    .status(t.getStatus())
                    .build());
        }
        return trainingSectionResponseModels;
    }

    private Employee getEmployeeFromDb(String email){
        return employeeService.findByUserEmail(email);
    }

}
