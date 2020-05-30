package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestModel;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseModel;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingSection;
import kg.sabyrov.terrafit.repository.SubscriptionRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.TrainingSectionService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;
    private final TrainingSectionService trainingSectionService;
    private final UserService userService;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, TrainingSectionService trainingSectionService, UserService userService) {
        this.subscriptionRepository = subscriptionRepository;
        this.trainingSectionService = trainingSectionService;
        this.userService = userService;
    }

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

    @Override
    public Subscription findByCode(String code) {
        return subscriptionRepository.findByCode(code);
    }

    @Override
    public SubscriptionResponseModel create(SubscriptionRequestModel subscriptionRequestModel) {
        TrainingSection trainingSection = trainingSectionService.getById(subscriptionRequestModel.getTrainingSectionId());
        Subscription subscription = Subscription.builder()
                .trainingSection(trainingSection)
                .sessionQuantity(subscriptionRequestModel.getSessionQuantity())
                .code(generateCode())
                .discountPercentages()
                .build();

    }
    private String generateCode(){
        return " ";
    }
    private BigDecimal getMultiplierForPrice(Integer sessionQuantity){
        if(sessionQuantity == 12) return new BigDecimal(1);
        else return new BigDecimal(3);
    }


}
