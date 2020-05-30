package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.userDto.UserSubscriptionModel;
import kg.sabyrov.terrafit.dto.visitDto.VisitDto;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;

public interface VisitService extends BaseService<Visit> {
    UserSubscriptionModel create(String code) throws SubscriptionNotFoundException;
}
