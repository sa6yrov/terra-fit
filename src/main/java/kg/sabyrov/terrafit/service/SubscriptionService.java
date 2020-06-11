package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestDto;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;

import java.util.List;

public interface SubscriptionService extends BaseService<Subscription> {
    List<SubscriptionResponseDto> getAllModels();

    SubscriptionResponseDto create(SubscriptionRequestDto subscriptionRequestDto) throws UserNotFoundException, SubscriptionNotFoundException;

    SubscriptionResponseDto getModelById(Long id) throws SubscriptionNotFoundException;
}
