package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseDto;
import kg.sabyrov.terrafit.dto.userDto.UserSubscriptionResponseDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.repository.VisitRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public UserSubscriptionResponseDto create(Long id) throws SubscriptionNotFoundException {
        if(!checkCode(id)) throw new SubscriptionNotFoundException("Subscription with this code not found");

        Subscription subscription = visitProcess(id);

        User user = subscription.getUser();
        Visit visit = Visit.builder()
                .subscription(subscriptionService.save(subscription))
                .user(user)
                .build();
        save(visit);



        SubscriptionResponseDto subscriptionResponseDto = SubscriptionResponseDto.builder()
                .trainingName(subscription.getTrainingGroup().getName())
                .price(subscription.getTrainingGroup().getSubscriptionPrice())
                .discountPercentages(subscription.getDiscountPercentages())
                .totalAmount(subscription.getTotalAmount())
                .sessionQuantity(subscription.getSessionQuantity())
                .status(subscription.getStatus())
                .build();

        return UserSubscriptionResponseDto.builder()
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .birthDate(user.getBirthDate())
                .gender(user.getGender())
                .phoneNumber(user.getPhoneNumber())
                .subscriptionResponseDto(subscriptionResponseDto)
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
