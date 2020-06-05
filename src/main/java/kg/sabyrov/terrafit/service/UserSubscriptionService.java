package kg.sabyrov.terrafit.service;

import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.UserSubscription;

public interface UserSubscriptionService extends BaseService<UserSubscription> {
    UserSubscription findBySubscription(Subscription subscription);
}
