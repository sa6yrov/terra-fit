package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestFreezeDto;
import kg.sabyrov.terrafit.entity.RequestFreeze;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.RequestNotFoundException;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.models.ResponseMessage;
import kg.sabyrov.terrafit.repository.RequestFreezeRepository;
import kg.sabyrov.terrafit.service.RequestFreezeService;
import kg.sabyrov.terrafit.service.SubscriptionService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestFreezeServiceImpl implements RequestFreezeService {

    @Autowired
    private RequestFreezeRepository requestFreezeRepository;

    @Autowired
    private SubscriptionService subscriptionService;

    @Autowired
    private UserService userService;

    @Override
    public RequestFreeze save(RequestFreeze requestFreeze) {
        return requestFreezeRepository.save(requestFreeze);
    }

    @Override
    public RequestFreeze getById(Long id) {
        Optional<RequestFreeze> requestFreezeOptional = requestFreezeRepository.findById(id);
        return requestFreezeOptional.orElse(null);
    }

    @Override
    public List<RequestFreeze> getAll() {
        return requestFreezeRepository.findAll();
    }

    @Override
    public List<FreezeResponseDto> getAllByStatus(Status status) {
        return requestFreezeRepository.findAllByStatus(status)
                .stream().map(this::mapRequestFreezeToModel).collect(Collectors.toList());
    }

    @Override
    public ResponseMessage create(RequestFreezeDto requestFreezeDto) throws UserNotFoundException {

        save(RequestFreeze.builder()
                .subscription(subscriptionService.getById(requestFreezeDto.getSubscriptionId()))
                .frozenDaysQuantity(requestFreezeDto.getDaysForFreeze())
                .status(Status.CONSIDERATION)
                .build());

        return ResponseMessage.builder().message("Your request was successfully sent").build();
    }

    @Override
    public ResponseMessage approving(List<Long> idList) throws RequestNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        User manager = userService.findByEmail(email);

        List<RequestFreeze> requestFreezes = approvedOrCancelledProcess(Status.APPROVED, idList, manager);
        freezingProcess(requestFreezes);
        return ResponseMessage.builder().message("Requests was successfully approved").build();
    }

    @Override
    public ResponseMessage cancelling(List<Long> idList) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        User manager = userService.findByEmail(email);


        return approvedOrCancelledProcess(Status.CANCELLED, idList, manager) != null ?
                new ResponseMessage("Requests was successfully cancelled") : new ResponseMessage("Requests for freezing ");
    }

    private void freezingProcess(List<RequestFreeze> requestFreezes) throws RequestNotFoundException {
        if (requestFreezes == null) throw new RequestNotFoundException("Requests for freezing subscription not found");
        List<Subscription> subscriptions = new ArrayList<>();

        for (RequestFreeze r : requestFreezes) {
            Subscription subscription = r.getSubscription();
            subscription.setStatus(Status.FROZEN);
            subscription.setFrozenUntilDate(LocalDate.now().plusDays(r.getFrozenDaysQuantity()));
            subscription.setExpirationDate(subscription.getExpirationDate().plusDays(r.getFrozenDaysQuantity()));
        }
        subscriptionService.saveAll(subscriptions);
    }

    private FreezeResponseDto mapRequestFreezeToModel(RequestFreeze requestFreeze) {
        return FreezeResponseDto.builder()
                .id(requestFreeze.getId())
                .userEmail(requestFreeze.getSubscription().getUser().getEmail())
                .subscriptionId(requestFreeze.getSubscription().getId())
                .dateOfRequest(requestFreeze.getCreatedDate())
                .daysForFreeze(requestFreeze.getFrozenDaysQuantity())
                .status(requestFreeze.getStatus())
                .build();
    }


    private List<RequestFreeze> approvedOrCancelledProcess(Status status, List<Long> idList, User manager) {
        try {
            List<RequestFreeze> freezeList = requestFreezeRepository.findAllByStatus(Status.CONSIDERATION);

            List<RequestFreeze> listForApproving = new ArrayList<>();
            for (Long l : idList) {
                for (RequestFreeze r : freezeList) {
                    if (l.equals(r.getId())) {
                        r.setStatus(status);
                        r.setNotify(0);
                        r.setManager(manager);
                        listForApproving.add(r);
                    }
                }
            }
            requestFreezeRepository.saveAll(listForApproving);
            return freezeList;
        } catch (Exception e) {
            return null;
        }
    }
}