package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.subscriptionDto.*;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestByGroupAndTwoTimesDto;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.exceptions.WrongBalanceException;

import java.util.List;

public interface SubscriptionService extends BaseService<Subscription> {
    List<SubscriptionResponseDto> getAllModels();

    SubscriptionResponseDto create(SubscriptionRequestDto subscriptionRequestDto) throws UserNotFoundException, SubscriptionNotFoundException, WrongBalanceException;


    List<SubscriptionResponseDto> getAllByUser();

    SubscriptionResponseDto findModelById(Long id) throws SubscriptionNotFoundException;

    TotalAmountBetweenDateResponseDto getTotalAmountByTwoDate(RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto);

    TotalAmountByTrainingGroupResponseDto getSumByTwoDateAndGroup(VisitRequestByGroupAndTwoTimesDto visitRequestByGroupAndTwoTimesDto);
    List<Subscription> saveAll(List<Subscription> subscriptions);

    List<Subscription> getAllByStatus(Status status);
}
