package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestDto;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseDto;
import kg.sabyrov.terrafit.entity.PromoCode;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingSection;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.repository.SubscriptionRepository;
import kg.sabyrov.terrafit.service.PromoCodeService;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.TrainingSectionService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    public List<SubscriptionResponseDto> getAllModels() {
        List<Subscription> subscriptions = getAll();
        List<SubscriptionResponseDto> subscriptionResponses = new ArrayList<>();

        for (Subscription s : subscriptions) {
            subscriptionResponses.add(SubscriptionResponseDto.builder()
                    .trainingName(s.getTrainingSection().getName())
                    .userEmail(s.getUser().getEmail())
                    .subscriptionId(s.getId())
                    .sessionQuantity(s.getSessionQuantity())
                    .price(s.getTrainingSection().getSubscriptionPrice())
                    .discountPercentages(s.getDiscountPercentages())
                    .totalAmount(s.getTotalAmount())
                    .build());
        }

        return subscriptionResponses;
    }

    @Override
    public SubscriptionResponseDto create(SubscriptionRequestDto subscriptionRequestDto) {
        TrainingSection trainingSection = trainingSectionService.getById(subscriptionRequestDto.getTrainingSectionId());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User user = userService.findByEmail(email);
        Subscription subscription = save(Subscription.builder()
                .trainingSection(trainingSection)
                .user(user)
                .sessionQuantity(subscriptionRequestDto.getSessionQuantity())
                .discountPercentages(getDiscountPercentages(subscriptionRequestDto.getPromoCode()))
                .totalAmount(getTotalPrice(trainingSection, subscriptionRequestDto))
                .build());
        return getSubscriptionResponse(subscription);

    }
    private BigDecimal getTotalPrice(TrainingSection trainingSection, SubscriptionRequestDto subscriptionRequestDto){
        if(trainingSection.getTrainingGroupCategory().getName().equals("Тренажерный зал") && subscriptionRequestDto.getSessionQuantity() == 1) return new BigDecimal(200);
        if(trainingSection.getTrainingGroupCategory().getName().equals("Фитнесс-группы") && subscriptionRequestDto.getSessionQuantity() == 1) return new BigDecimal(300);
        BigDecimal discountPrice = (trainingSection.getSubscriptionPrice().multiply(getMultiplierForPrice(subscriptionRequestDto.getSessionQuantity())))
                    .multiply(new BigDecimal(100 / getDiscountPercentages(subscriptionRequestDto.getPromoCode())));
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

    private SubscriptionResponseDto getSubscriptionResponse(Subscription subscription){
        return SubscriptionResponseDto.builder()
                .trainingName(subscription.getTrainingSection().getName())
                .userEmail(subscription.getUser().getEmail())
                .sessionQuantity(subscription.getSessionQuantity())
                .subscriptionId(subscription.getId())
                .price(subscription.getTrainingSection().getSubscriptionPrice())
                .discountPercentages(subscription.getDiscountPercentages())
                .totalAmount(subscription.getTotalAmount())
                .build();
    }

}
