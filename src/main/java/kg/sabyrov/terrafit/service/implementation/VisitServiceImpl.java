package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionIdDto;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.Visit;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.repository.VisitRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.TrainingGroupService;
import kg.sabyrov.terrafit.service.UserService;
import kg.sabyrov.terrafit.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitServiceImpl implements VisitService {
    @Autowired
    private VisitRepository visitRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @Autowired
    private TrainingGroupService trainingGroupService;

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
    public VisitResponseDto create(SubscriptionIdDto subscriptionIdDto) throws SubscriptionNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User manager = userService.findByEmail(email);
        if(!checkCode(subscriptionIdDto.getCode()))
            throw new SubscriptionNotFoundException("Subscription with this code is inactive or not found");

        Subscription subscription = visitProcess(subscriptionIdDto.getCode());

        User user = subscription.getUser();
        Visit visit = Visit.builder()
                .subscription(subscriptionService.save(subscription))
                .user(user)
                .manager(manager)
                .build();


        return mapVisitToModel(save(visit)); //'getModel' have Visit param, 'save(visit) return visit from DB'
    }

    @Override
    public List<VisitResponseDto> getAllVisitsBetweenTime(RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto) {
        return visitRepository.findAllByCreatedDateBetween(
                requestTwoLocalDateTimeDto.getFrom(),
                requestTwoLocalDateTimeDto.getTo()
        ).stream().map(this::mapVisitToModel).collect(Collectors.toList());
    }

    @Override
    public List<VisitResponseDto> getAllModels() {
        return getAll().stream().map(this::mapVisitToModel).collect(Collectors.toList());
    }

    @Override
    public List<VisitResponseDto> findAllBySubscription(Long id) {
        Subscription subscription = subscriptionService.getById(id);

        return visitRepository.findAllBySubscription(subscription)
                .stream().map(this::mapVisitToModel).collect(Collectors.toList());
    }

    @Override
    public List<VisitResponseDto> findAllByTrainingGroupAndBetweenTime(Long id, RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto) {
        TrainingGroup trainingGroup = trainingGroupService.getById(id);

        return visitRepository.findAllBySubscription_TrainingGroupAndCreatedDateBetween(
                trainingGroup,
                requestTwoLocalDateTimeDto.getFrom(),
                requestTwoLocalDateTimeDto.getTo()
        ).stream().map(this::mapVisitToModel).collect(Collectors.toList());
    }

    private boolean checkCode(Long id) {
        Subscription subscription = subscriptionService.getById(id);
        return subscription != null && !subscription.getStatus().equals(Status.INACTIVE);
    }

    private Subscription visitProcess(Long id) {
        Subscription subscription = subscriptionService.getById(id);
        subscription.setSessionQuantity(subscription.getSessionQuantity() - 1);
        if(subscription.getSessionQuantity() == 0) subscription.setStatus(Status.INACTIVE);
        return subscription;
    }

    private VisitResponseDto mapVisitToModel(Visit visit){
        return VisitResponseDto.builder()
                .email(visit.getUser().getEmail())
                .name(visit.getUser().getName())
                .surname(visit.getUser().getSurname())
                .trainingGroupName(visit.getSubscription().getTrainingGroup().getName())
                .subscriptionId(visit.getSubscription().getId())
                .sessionQuantity(visit.getSubscription().getSessionQuantity())
                .manager(visit.getManager().getEmail())
                .visitTime(visit.getCreatedDate())
                .build();
    }
}
