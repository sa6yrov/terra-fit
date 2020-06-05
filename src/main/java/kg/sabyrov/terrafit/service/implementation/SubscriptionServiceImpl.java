package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestModel;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseModel;
import kg.sabyrov.terrafit.entity.PromoCode;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingSection;
import kg.sabyrov.terrafit.repository.SubscriptionRepository;
import kg.sabyrov.terrafit.service.PromoCodeService;
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
    private final PromoCodeService promoCodeService;

    @Autowired
    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, TrainingSectionService trainingSectionService, UserService userService, PromoCodeService promoCodeService) {
        this.subscriptionRepository = subscriptionRepository;
        this.trainingSectionService = trainingSectionService;
        this.userService = userService;
        this.promoCodeService = promoCodeService;
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
    public SubscriptionResponseModel create(SubscriptionRequestModel subscriptionRequestModel) {
        TrainingSection trainingSection = trainingSectionService.getById(subscriptionRequestModel.getTrainingSectionId());
        Subscription subscription = Subscription.builder()
                .trainingSection(trainingSection)
                .sessionQuantity(subscriptionRequestModel.getSessionQuantity())
                .discountPercentages(0)
                .totalAmount(getTotalPrice(trainingSection, subscriptionRequestModel))
                .build();
        return null;

    }
    private BigDecimal getTotalPrice(TrainingSection trainingSection,SubscriptionRequestModel subscriptionRequestModel){
        if(trainingSection.getTrainingGroupCategory().getName().equals("Тренажерный зал") && subscriptionRequestModel.getSessionQuantity() == 1) return new BigDecimal(200);
        if(trainingSection.getTrainingGroupCategory().getName().equals("Фитнесс-группы") && subscriptionRequestModel.getSessionQuantity() == 1) return new BigDecimal(300);
        BigDecimal discountPrice = (trainingSection.getSubscriptionPrice().multiply(getMultiplierForPrice(subscriptionRequestModel.getSessionQuantity())))
                    .multiply(new BigDecimal(100 / getDiscountPercentages(subscriptionRequestModel.getPromoCode())));
        return trainingSection.getSubscriptionPrice().subtract(discountPrice);
    }

    private BigDecimal getMultiplierForPrice(Integer sessionQuantity){
        switch (sessionQuantity){
            case 12 : return new BigDecimal(1);
            case 24 : return new BigDecimal(2);
            case 36 : return new BigDecimal(3);
        }
        return BigDecimal.ZERO;
    }

    private Integer getDiscountPercentages(String promoCode){
        PromoCode promoCodeFromDb = promoCodeService.findByName(promoCode);
        return promoCodeFromDb.getDiscountPercentages();
    }
}
