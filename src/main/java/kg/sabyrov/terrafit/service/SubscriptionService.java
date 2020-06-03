package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestModel;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseModel;
import kg.sabyrov.terrafit.entity.Subscription;

public interface SubscriptionService extends BaseService<Subscription> {


    SubscriptionResponseModel create(SubscriptionRequestModel subscriptionRequestModel);
}
