package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.dto.requestForFreezeDto.RequestApprovingCancellingDto;
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

import javax.mail.MessagingException;
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

    @Autowired
    private HtmlMessageSenderService htmlMessageSenderService;

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
    public List<FreezeResponseDto> getAllModelByStatusConsideration() throws RequestNotFoundException {
        List<FreezeResponseDto> list = requestFreezeRepository.findAllByStatus(Status.CONSIDERATION)
                .stream().map(this::mapRequestFreezeToModel).collect(Collectors.toList());
        if (list.isEmpty()) throw new RequestNotFoundException("Requests with status 'CONSIDERATION' not found");
        return list;
    }

    @Override
    public List<RequestFreeze> getAllByStatus(Status s) {
        return requestFreezeRepository.findAllByStatus(s);
    }

    @Override
    public ResponseMessage create(RequestFreezeDto requestFreezeDto){

        save(RequestFreeze.builder()
                .subscription(subscriptionService.getById(requestFreezeDto.getSubscriptionId()))
                .frozenDaysQuantity(requestFreezeDto.getDaysForFreeze())
                .status(Status.CONSIDERATION)
                .build());

        return ResponseMessage.builder().message("Your request was successfully sent").build();
    }

    @Override
    public ResponseMessage approving(RequestApprovingCancellingDto requestApprovingCancellingDto) throws RequestNotFoundException, MessagingException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        User manager = userService.findByEmail(email);

        List<RequestFreeze> requestFreezes = approvedOrCancelledProcess(Status.APPROVED, requestApprovingCancellingDto.getIdList(), manager);
        freezingProcess(requestFreezes);
        sendMessageForUsers(requestFreezes, "Your request was successfully approved");
        return ResponseMessage.builder().message("Requests was successfully approved").build();
    }

    @Override
    public ResponseMessage cancelling(RequestApprovingCancellingDto requestApprovingCancellingDto) throws MessagingException, RequestNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();

        User manager = userService.findByEmail(email);

        List<RequestFreeze> requestFreezes = approvedOrCancelledProcess(Status.CANCELLED, requestApprovingCancellingDto.getIdList(), manager);

        sendMessageForUsers(requestFreezes, "Your request was cancelled");
        return new ResponseMessage("Requests was successfully cancelled");

    }

    private void sendMessageForUsers(List<RequestFreeze> list, String message) throws MessagingException {
        for (RequestFreeze r : list) {
            htmlMessageSenderService.sendHtmlEmail(message, r.getSubscription().getUser().getEmail());
        }
    }

    private void freezingProcess(List<RequestFreeze> requestFreezes) {
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


    private List<RequestFreeze> approvedOrCancelledProcess(Status status, List<Long> idList, User manager) throws RequestNotFoundException {
        List<RequestFreeze> freezeList = getAllByStatus(Status.CONSIDERATION);
        if (freezeList.isEmpty() || idList.isEmpty()) throw new RequestNotFoundException("Requests for freezing subscription not found");
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
        return requestFreezeRepository.saveAll(listForApproving);
    }
}
