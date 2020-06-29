package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.visitDto.RequestTwoLocalDateTimeDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitHistoryResponseDto;
import kg.sabyrov.terrafit.dto.visitDto.VisitResponseDto;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.TrainingGroup;
import kg.sabyrov.terrafit.entity.VisitHistory;
import kg.sabyrov.terrafit.repository.VisitHistoryRepository;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.TrainingGroupService;
import kg.sabyrov.terrafit.service.VisitHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VisitHistoryServiceImpl implements VisitHistoryService {

    @Autowired
    private VisitHistoryRepository visitHistoryRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private TrainingGroupService trainingGroupService;

    @Override
    public VisitHistory save(VisitHistory visitHistory) {
        return visitHistoryRepository.save(visitHistory);
    }

    @Override
    public VisitHistory getById(Long id) {
        Optional<VisitHistory> visitHistoryOptional = visitHistoryRepository.findById(id);
        return visitHistoryOptional.orElse(null);
    }

    @Override
    public List<VisitHistory> getAll() {
        return visitHistoryRepository.findAll();
    }

    @Override
    public List<VisitHistoryResponseDto> getAllVisitsBetweenTime(RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto) {
        return visitHistoryRepository.findAllByVisitTimeBetween(
                requestTwoLocalDateTimeDto.getFrom(),
                requestTwoLocalDateTimeDto.getTo())
                .stream().map(this::mapVisitHistoryToModel).collect(Collectors.toList());
    }

    @Override
    public List<VisitHistoryResponseDto> getAllModels() {
        return getAll().stream().map(this::mapVisitHistoryToModel).collect(Collectors.toList());
    }

    @Override
    public List<VisitHistoryResponseDto> findAllBySubscription(Long id) {
        Subscription subscription = subscriptionService.getById(id);

        return visitHistoryRepository.findAllByVisit_Subscription(subscription)
                .stream().map(this::mapVisitHistoryToModel).collect(Collectors.toList());
    }

    @Override
    public List<VisitHistoryResponseDto> findAllByTrainingGroupAndBetweenTime(Long id, RequestTwoLocalDateTimeDto requestTwoLocalDateTimeDto) {
        TrainingGroup trainingGroup = trainingGroupService.getById(id);
        return visitHistoryRepository.findAllByVisitTimeBetweenAndVisit_Subscription_TrainingGroup(
                requestTwoLocalDateTimeDto.getFrom(),
                requestTwoLocalDateTimeDto.getTo(),
                trainingGroup)
                .stream().map(this::mapVisitHistoryToModel).collect(Collectors.toList());
    }

    private VisitHistoryResponseDto mapVisitHistoryToModel(VisitHistory visitHistory){
        return VisitHistoryResponseDto.builder()
                .managerEmail(visitHistory.getManager().getEmail())
                .visitorEmail(visitHistory.getVisit().getSubscription().getUser().getEmail())
                .subscriptionId(visitHistory.getVisit().getSubscription().getId())
                .visitTime(visitHistory.getVisit().getCreatedDate())
                .sessionQuantityAfterVisit(visitHistory.getSessionQuantityAfterVisit())
                .subtractedSessions(visitHistory.getSubtractedSessions())
                .build();
    }
}
