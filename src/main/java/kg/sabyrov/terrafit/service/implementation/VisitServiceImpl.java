package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseModel;
import kg.sabyrov.terrafit.dto.userDto.UserSubscriptionModel;
import kg.sabyrov.terrafit.dto.visitDto.VisitDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.UserSubscription;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.repository.VisitRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.UserSubscriptionService;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final SubscriptionService subscriptionService;
    private final UserSubscriptionService userSubscriptionService;

    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository, SubscriptionService subscriptionService, UserSubscriptionService userSubscriptionService) {
        this.visitRepository = visitRepository;
        this.subscriptionService = subscriptionService;
        this.userSubscriptionService = userSubscriptionService;

    }

    @Override
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public Visit getById(Long id) {
        Optional<Visit> visitOptional = visitRepository.findById(id);
        return visitOptional.orElse(null);
    }

    @Override
    public List<Visit> getAll() {
        return visitRepository.findAll();
    }

    @Override
    public UserSubscriptionModel create(String code) throws SubscriptionNotFoundException {
        if(!checkCode(code)) throw new SubscriptionNotFoundException("Subscription with this code not found");

        Subscription subscription = visitProcess(code);
        UserSubscription userSubscription = userSubscriptionService.findBySubscription(subscription);
        User user = userSubscription.getUser();
        Visit visit = Visit.builder()
                .subscription(subscriptionService.save(subscription))
                .user(user)
                .visitDate(new Date())
                .build();
        save(visit);

        SubscriptionResponseModel subscriptionResponseModel = SubscriptionResponseModel.builder()
                .trainingName(subscription.getTrainingSection().getName())
                .code(subscription.getCode())
                .price(subscription.getTrainingSection().getSubscriptionPrice())
                .discountPercentages(subscription.getDiscountPercentages())
                .totalAmount(userSubscription.getPrice())
                .sessionQuantity(subscription.getSessionQuantity())
                .status(subscription.getStatus())
                .build();

        UserSubscriptionModel userSubscriptionModel = UserSubscriptionModel.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .subscriptionResponseModel(subscriptionResponseModel)
                .build();

        return userSubscriptionModel;
    }
    private boolean checkCode(String code){
        return subscriptionService.findByCode(code) == null;
    }
    private Subscription visitProcess(String code){
        Subscription subscription = subscriptionService.findByCode(code);
        subscription.setSessionQuantity(subscription.getSessionQuantity() - 1);
        return subscription;
    }
}
