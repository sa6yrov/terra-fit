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
        TrainingGroup trainingGroup = saveAndGetTrainingSectionFromDb(trainingGroupRequestDto);

        return TrainingGroupResponseDto.builder()
                .name(trainingGroup.getName())
                .coachName(trainingGroup.getEmployee().getUser().getName())
                .status(trainingGroup.getStatus())
                .build();

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
        List<TrainingGroup> trainingGroups = getAll();
        List<TrainingGroupResponseDto> trainingGroupResponseDtos = new ArrayList<>();

        for (TrainingGroup t : trainingGroups) {
            trainingGroupResponseDtos.add(TrainingGroupResponseDto.builder()
                    .id(t.getId())
                    .name(t.getName())
                    .subscriptionPrice(t.getSubscriptionPrice())
                    .coachName(t.getEmployee().getUser().getName())
                    .status(t.getStatus())
                    .build());
        }
        return trainingGroupResponseDtos;
    }

    private Employee getEmployeeFromDb(String email){
        return employeeService.findByUserEmail(email);
    }

}
