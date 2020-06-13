package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionRequestDto;
import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseDto;
import kg.sabyrov.terrafit.entity.PromoCode;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.repository.SubscriptionRepository;
import kg.sabyrov.terrafit.service.PromoCodeService;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.TrainingGroupService;
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
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private TrainingGroupService trainingGroupService;
    @Autowired
    private UserService userService;
    @Autowired
    private PromoCodeService promoCodeService;

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getById(Long id) throws SubscriptionNotFoundException {
        Optional<Subscription> subscriptionOptional = subscriptionRepository.findById(id);
        if(subscriptionOptional.orElse(null) == null) throw new SubscriptionNotFoundException("Subscription with '" + id + "' id not found");
        return subscriptionOptional.get();
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
                    .trainingName(s.getTrainingGroup().getName())
                    .userEmail(s.getUser().getEmail())
                    .subscriptionId(s.getId())
                    .sessionQuantity(s.getSessionQuantity())
                    .price(s.getTrainingGroup().getSubscriptionPrice())
                    .discountPercentages(s.getDiscountPercentages())
                    .totalAmount(s.getTotalAmount())
                    .build());
        }

        return subscriptionResponses;
    }

    @Override
    public SubscriptionResponseDto create(SubscriptionRequestDto subscriptionRequestDto) throws UserNotFoundException, SubscriptionNotFoundException {
        TrainingGroup trainingGroup = trainingGroupService.getById(subscriptionRequestDto.getTrainingSectionId());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User user = userService.findByEmail(email);
        Subscription subscription = save(Subscription.builder()
                .trainingGroup(trainingGroup)
                .user(user)
                .sessionQuantity(subscriptionRequestDto.getSessionQuantity())
                .discountPercentages(getDiscountPercentages(subscriptionRequestDto.getPromoCode()))
                .totalAmount(getTotalPrice(trainingGroup, subscriptionRequestDto))
                .build());
        return getSubscriptionResponse(subscription);

    }

    @Override
    public SubscriptionResponseDto getModelById(Long id) throws SubscriptionNotFoundException {
        Subscription subscription = getById(id);
        return SubscriptionResponseDto.builder()
                .subscriptionId(subscription.getId())
                .userEmail(subscription.getUser().getEmail())
                .trainingName(subscription.getTrainingGroup().getName())
                .sessionQuantity(subscription.getSessionQuantity())
                .price(subscription.getTrainingGroup().getSubscriptionPrice())
                .discountPercentages(subscription.getDiscountPercentages())
                .totalAmount(subscription.getTotalAmount())
                .status(subscription.getStatus())
                .build();
    }

    private BigDecimal getTotalPrice(TrainingGroup trainingGroup, SubscriptionRequestDto subscriptionRequestDto){
        if(trainingGroup.getTrainingGroupCategory().getName().equals("Тренажерный зал") && subscriptionRequestDto.getSessionQuantity() == 1) return new BigDecimal(200);
        if(trainingGroup.getTrainingGroupCategory().getName().equals("Фитнесс-группы") && subscriptionRequestDto.getSessionQuantity() == 1) return new BigDecimal(300);
        BigDecimal discountPrice = (trainingGroup.getSubscriptionPrice().multiply(getMultiplierForPrice(subscriptionRequestDto.getSessionQuantity())))
                    .multiply(new BigDecimal(100 / getDiscountPercentages(subscriptionRequestDto.getPromoCode())));
        return trainingGroup.getSubscriptionPrice().subtract(discountPrice);
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
                .trainingName(subscription.getTrainingGroup().getName())
                .userEmail(subscription.getUser().getEmail())
                .sessionQuantity(subscription.getSessionQuantity())
                .subscriptionId(subscription.getId())
                .price(subscription.getTrainingGroup().getSubscriptionPrice())
                .discountPercentages(subscription.getDiscountPercentages())
                .totalAmount(subscription.getTotalAmount())
                .build();
    }

}
