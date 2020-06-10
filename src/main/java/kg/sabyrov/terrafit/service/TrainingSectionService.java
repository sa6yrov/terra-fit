package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionRequestDto;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionResponseDto;
import kg.sabyrov.terrafit.entity.TrainingSection;

import java.util.List;

public interface TrainingSectionService extends BaseService<TrainingSection> {
    List<TrainingSectionResponseDto> getAllModel();

    TrainingSectionResponseDto create(TrainingSectionRequestDto trainingSectionRequestDto);
}
