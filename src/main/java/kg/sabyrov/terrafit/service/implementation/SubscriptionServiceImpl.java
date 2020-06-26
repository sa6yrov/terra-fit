package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.*;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestByGroupAndTwoTimesDto;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.entity.PromoCode;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.exceptions.WrongBalanceException;
import kg.sabyrov.terrafit.repository.SubscriptionRepository;
import kg.sabyrov.terrafit.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private PaymentService paymentService;

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

        return getSubResponseList(subscriptions);

    }

    @Override
    public SubscriptionResponseDto create(SubscriptionRequestDto subscriptionRequestDto) throws WrongBalanceException, UserNotFoundException {
        TrainingGroup trainingGroup = trainingGroupService.getById(subscriptionRequestDto.getTrainingGroupId());
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User user = userService.findByEmail(email);

        if(!paymentService.isPaid(user, getTotalPrice(trainingGroup, subscriptionRequestDto))) throw new WrongBalanceException("Your balance is zero, please top up and make the payment again");
        Subscription subscription = save(Subscription.builder()
                .trainingGroup(trainingGroup)
                .user(user)
                .sessionQuantity(subscriptionRequestDto.getSessionQuantity())
                .discountPercentages(getDiscountPercentages(subscriptionRequestDto.getPromoCode()))
                .totalAmount(getTotalPrice(trainingGroup, subscriptionRequestDto))
                .status(Status.ACTIVE)
                .build());

        return getSubscriptionResponse(subscription);

    }


    @Override
    public List<SubscriptionResponseDto> getAllByUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User user = userService.findByEmail(email);
        List<Subscription> subscriptions = subscriptionRepository.findAllByUser(user);

        return getSubResponseList(subscriptions);
    }

    @Override
    public SubscriptionResponseDto findModelById(Long id) throws SubscriptionNotFoundException {
        if(getById(id) == null) throw new SubscriptionNotFoundException("Subscription with '" + id + "' id not found");
        return getSubscriptionResponse(getById(id));
    }

    @Override
    public TotalAmountBetweenDateResponseDto getTotalAmountByTwoDate(RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto) {
        return TotalAmountBetweenDateResponseDto.builder()
                .totalAmount(subscriptionRepository.getSumByTwoDate(requestTwoLocalDateTimeDto.getFrom(), requestTwoLocalDateTimeDto.getTo()))
                .build();
    }

    @Override
    public TotalAmountByTrainingGroupResponseDto getSumByTwoDateAndGroup(VisitRequestByGroupAndTwoTimesDto visitRequestByGroupAndTwoTimesDto) {
        TrainingGroup trainingGroup = trainingGroupService.getById(visitRequestByGroupAndTwoTimesDto.getTrainingGroupId());
        return TotalAmountByTrainingGroupResponseDto.builder()
                .trainingGroupName(trainingGroup.getName())
                .amount(subscriptionRepository.getSumByTwoDateAndGroup(visitRequestByGroupAndTwoTimesDto.getFrom(), visitRequestByGroupAndTwoTimesDto.getTo(), trainingGroup))
                .build();
    }


    private List<SubscriptionResponseDto> getSubResponseList(List<Subscription> subscriptions){
        List<SubscriptionResponseDto> subscriptionResponseDtos = new ArrayList<>();
        for (Subscription s : subscriptions) {
            subscriptionResponseDtos.add(getSubscriptionResponse(s));
        }
        return subscriptionResponseDtos;
    }


    private BigDecimal getTotalPrice(TrainingGroup trainingGroup, SubscriptionRequestDto subscriptionRequestDto){
        if(trainingGroup.getTrainingGroupCategory().getName().equals("Тренажерный зал") && subscriptionRequestDto.getSessionQuantity() == 1) return new BigDecimal(200);

        if(trainingGroup.getTrainingGroupCategory().getName().equals("Фитнесс-группы") && subscriptionRequestDto.getSessionQuantity() == 1) return new BigDecimal(300);
        BigDecimal price = trainingGroup.getSubscriptionPrice().multiply(getMultiplierForPrice(subscriptionRequestDto.getSessionQuantity()));
        BigDecimal discountPrice = price.multiply(new BigDecimal(getDiscountPercentages(subscriptionRequestDto.getPromoCode()) / 100.0));
        return price.subtract(discountPrice).setScale(1, RoundingMode.UP);
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
        return promoCodeFromDb == null ? 0 : promoCodeFromDb.getDiscountPercentages();
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
                .status(subscription.getStatus())
                .build();
    }

}
