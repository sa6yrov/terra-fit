package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.repository.SubscriptionRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getById(Long id) {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(id);
        return subscriptionOptional.orElse(null);
    }

    @Override
    public List<Subscription> getAll() {

        return subscriptionRepository.findAll();
    }
}
