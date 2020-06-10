package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionRequestDto;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionResponseDto;
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
    public TrainingSectionResponseDto create(TrainingSectionRequestDto trainingSectionRequestDto) {
        TrainingSection trainingSection = saveAndGetTrainingSectionFromDb(trainingSectionRequestDto);

        return TrainingSectionResponseDto.builder()
                .name(trainingSection.getName())
                .coachName(trainingSection.getEmployee().getUser().getName())
                .status(trainingSection.getStatus())
                .build();

    }
    private TrainingSection saveAndGetTrainingSectionFromDb(TrainingSectionRequestDto trainingSectionRequestDto){
        TrainingSection trainingSection = TrainingSection.builder()
                .name(trainingSectionRequestDto.getName())
                .employee(getEmployeeFromDb(trainingSectionRequestDto.getEmployeeEmail()))
                .status(trainingSectionRequestDto.getStatus())
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
    public List<TrainingSectionResponseDto> getAllModel() {
        List<TrainingSection> trainingSections = getAll();
        List<TrainingSectionResponseDto> trainingSectionResponseDtos = new ArrayList<>();

        for (TrainingSection t : trainingSections) {
            trainingSectionResponseDtos.add(TrainingSectionResponseDto.builder()
                    .name(t.getName())
                    .coachName(t.getEmployee().getUser().getName())
                    .status(t.getStatus())
                    .build());
        }
        return trainingSectionResponseDtos;
    }

    private Employee getEmployeeFromDb(String email){
        return employeeService.findByUserEmail(email);
    }

}
