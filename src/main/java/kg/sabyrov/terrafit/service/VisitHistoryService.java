package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitHistoryResponseDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.VisitHistory;

import java.util.List;

public interface VisitHistoryService extends BaseService<VisitHistory> {
    List<VisitHistoryResponseDto> getAllVisitsBetweenTime(RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto);

    List<VisitHistoryResponseDto> getAllModels();

    List<VisitHistoryResponseDto> findAllBySubscription(Long id);

    List<VisitHistoryResponseDto> findAllByTrainingGroupAndBetweenTime(Long id, RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto);
}
