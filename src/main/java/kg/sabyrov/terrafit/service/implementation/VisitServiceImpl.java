package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseDto;
import kg.sabyrov.terrafit.dto.userDto.UserSubscriptionResponseDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitRequestTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.repository.VisitRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VisitServiceImpl implements VisitService {
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private SubscriptionService subscriptionService;


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
    public UserSubscriptionResponseDto create(VisitDto visitDto) throws SubscriptionNotFoundException {
        if(!checkCode(visitDto.getCode())) throw new SubscriptionNotFoundException("Subscription with this code not found");

        Subscription subscription = visitProcess(visitDto.getCode());

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

    @Override
    public List<VisitResponseDto> getAllVisitsBetweenTime(VisitRequestTimeDto visitRequestTimeDto) {
        List<Visit> visits = visitRepository.findAllByCreatedDateBetween(visitRequestTimeDto.getFrom(), visitRequestTimeDto.getTo());
        List<VisitResponseDto> visitResponseDtos = new ArrayList<>();

        for (Visit v : visits) {
            visitResponseDtos.add(VisitResponseDto.builder()
                    .email(v.getUser().getEmail())
                    .name(v.getUser().getName())
                    .surname(v.getUser().getSurname())
                    .trainingGroup(v.getSubscription().getTrainingGroup().getName())
                    .subscriptionId(v.getSubscription().getId())
                    .build());
        }
        return visitResponseDtos;
    }

    private boolean checkCode(Long id) throws SubscriptionNotFoundException {
        return subscriptionService.getById(id) == null;
    }

    private Subscription visitProcess(Long id) throws SubscriptionNotFoundException {
        Subscription subscription = subscriptionService.getById(id);
        subscription.setSessionQuantity(subscription.getSessionQuantity() - 1);
        return subscription;
    }

}
