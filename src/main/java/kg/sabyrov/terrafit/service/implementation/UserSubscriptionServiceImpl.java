package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.UserSubscription;
import kg.sabyrov.terrafit.repository.UserSubscriptionRepository;
import kg.sabyrov.terrafit.service.UserSubscriptonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserSubscriptionServiceImpl implements UserSubscriptonService {
    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Override
    public UserSubscription save(UserSubscription userSubscription) {
        return userSubscriptionRepository.save(userSubscription);
    }

    @Override
    public UserSubscription getById(Long id) {
        Optional<UserSubscription> userSubscriptionOptional = userSubscriptionRepository.findById(id);
        return userSubscriptionOptional.orElse(null);
    }

    @Override
    public List<UserSubscription> getAll() {
        return userSubscriptionRepository.findAll();
    }
}
