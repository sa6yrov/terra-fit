package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.userDto.UserSubscriptionResponseDto;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;

public interface VisitService extends BaseService<Visit> {
    UserSubscriptionResponseDto create(Long id) throws SubscriptionNotFoundException;
}
