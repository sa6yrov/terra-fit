package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionIdDto;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;

import java.util.List;

public interface VisitService extends BaseService<Visit> {
    VisitResponseDto create(SubscriptionIdDto subscriptionIdDto) throws SubscriptionNotFoundException;

    List<VisitResponseDto> getAllVisitsBetweenTime(RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto);

    List<VisitResponseDto> getAllModels();

    List<VisitResponseDto> findAllBySubscription(Long id);

    List<VisitResponseDto> findAllByTrainingGroupAndBetweenTime(Long id, RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto);

}
