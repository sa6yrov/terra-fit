package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionIdDto;
import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.*;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.SubscriptionNotFoundException;
import kg.sabyrov.terrafit.repository.VisitRepository;
import kg.sabyrov.terrafit.service.*;
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

    @Autowired
    private VisitHistoryService visitHistoryService;

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

        Visit visit = save(Visit.builder()
                .subscription(subscriptionService.save(subscription))
                .build());

        visitHistoryService.save(VisitHistory.builder()
                .manager(manager)
                .visit(visit)
                .visitTime(visit.getCreatedDate())
                .sessionQuantityAfterVisit(subscription.getSessionQuantity())
                .subtractedSessions(1)
                .build());


        return mapVisitToModel(visit);
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
                .email(visit.getSubscription().getUser().getEmail())
                .name(visit.getSubscription().getUser().getName())
                .surname(visit.getSubscription().getUser().getSurname())
                .trainingGroupName(visit.getSubscription().getTrainingGroup().getName())
                .subscriptionId(visit.getSubscription().getId())
                .sessionQuantity(visit.getSubscription().getSessionQuantity())
                .visitTime(visit.getCreatedDate())
                .build();
    }
}
