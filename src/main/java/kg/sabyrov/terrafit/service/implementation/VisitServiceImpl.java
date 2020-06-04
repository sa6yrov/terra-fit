package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseModel;
import kg.sabyrov.terrafit.dto.userDto.UserSubscriptionModel;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.repository.VisitRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;
    private final SubscriptionService subscriptionService;


    @Autowired
    public VisitServiceImpl(VisitRepository visitRepository, SubscriptionService subscriptionService) {
        this.visitRepository = visitRepository;
        this.subscriptionService = subscriptionService;
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
    public UserSubscriptionModel create(Long id) throws SubscriptionNotFoundException {
        if(!checkCode(id)) throw new SubscriptionNotFoundException("Subscription with this code not found");

        Subscription subscription = visitProcess(id);

        User user = subscription.getUser();
        Visit visit = Visit.builder()
                .subscription(subscriptionService.save(subscription))
                .user(user)
                .build();
        save(visit);



        SubscriptionResponseModel subscriptionResponseModel = SubscriptionResponseModel.builder()
                .trainingName(subscription.getTrainingSection().getName())
                .price(subscription.getTrainingSection().getSubscriptionPrice())
                .discountPercentages(subscription.getDiscountPercentages())
                .totalAmount(subscription.getTotalAmount())
                .sessionQuantity(subscription.getSessionQuantity())
                .status(subscription.getStatus())
                .build();

        return UserSubscriptionModel.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .subscriptionResponseModel(subscriptionResponseModel)
                .build();
    }

    private boolean checkCode(Long id){
        return subscriptionService.getById(id) == null;
    }

    private Subscription visitProcess(Long id){
        Subscription subscription = subscriptionService.getById(id);
        subscription.setSessionQuantity(subscription.getSessionQuantity() - 1);
        return subscription;
    }

}
