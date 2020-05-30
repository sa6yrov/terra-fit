package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionRequestModel;
import kg.sabyrov.terrafit.dto.trainingsectionDto.TrainingSectionResponseModel;
import kg.sabyrov.terrafit.entity.TrainingSection;

import java.util.List;

public interface TrainingSectionService extends BaseService<TrainingSection> {
    List<TrainingSectionResponseModel> getAllModel();

    TrainingSectionResponseModel create(TrainingSectionRequestModel trainingSectionRequestModel);
}
