package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingGroupRequestDto;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingGroupResponseDto;
import kg.sabyrov.terrafit.entity.Employee;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.repository.TrainingGroupRepository;
import kg.sabyrov.terrafit.service.EmployeeService;
import kg.sabyrov.terrafit.service.TrainingGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingGroupServiceImpl implements TrainingGroupService {
    @Autowired
    private TrainingGroupRepository trainingGroupRepository;
    @Autowired
    private EmployeeService employeeService;


    @Override
    public TrainingGroup save(TrainingGroup trainingGroup) {
        return trainingGroupRepository.save(trainingGroup);
    }

    @Override
    public TrainingGroupResponseDto create(TrainingGroupRequestDto trainingGroupRequestDto) {

        return mapTrainingGroupToModel(saveAndGetTrainingSectionFromDb(trainingGroupRequestDto)); //saveAndGetTrainingSectionFromDb() return Entity

    }
    private TrainingGroup saveAndGetTrainingSectionFromDb(TrainingGroupRequestDto trainingGroupRequestDto){
        TrainingGroup trainingGroup = TrainingGroup.builder()
                .name(trainingGroupRequestDto.getName())
                .employee(getEmployeeFromDb(trainingGroupRequestDto.getEmployeeEmail()))
                .status(trainingGroupRequestDto.getStatus())
                .build();
        return save(trainingGroup);
    }

    @Override
    public TrainingGroup getById(Long id) {
        Optional<TrainingGroup> trainingSectionOptional = trainingGroupRepository.findById(id);
        return trainingSectionOptional.orElse(null);
    }

    @Override
    public List<TrainingGroup> getAll() {
        return trainingGroupRepository.findAll();
    }


    @Override
    public List<TrainingGroupResponseDto> getAllModel() {
        return getAll().stream()
                .map(this::mapTrainingGroupToModel)
                .collect(Collectors.toList());
    }

    private Employee getEmployeeFromDb(String email){
        return employeeService.findByUserEmail(email);
    }

    private TrainingGroupResponseDto mapTrainingGroupToModel(TrainingGroup t){
        return TrainingGroupResponseDto.builder()
                .id(t.getId())
                .name(t.getName())
                .subscriptionPrice(t.getSubscriptionPrice())
                .coachName(t.getEmployee().getUser().getName())
                .status(t.getStatus())
                .build();
    }

}
