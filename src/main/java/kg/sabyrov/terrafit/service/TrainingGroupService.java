package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingGroupRequestDto;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingGroupResponseDto;
import kg.sabyrov.terrafit.entity.TrainingGroup;

import java.util.List;

public interface TrainingGroupService extends BaseService<TrainingGroup> {
    List<TrainingGroupResponseDto> getAllModel();

    TrainingGroupResponseDto create(TrainingGroupRequestDto trainingGroupRequestDto);
}
