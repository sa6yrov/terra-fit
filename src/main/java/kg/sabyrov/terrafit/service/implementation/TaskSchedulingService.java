package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.RequestFreeze;
import kg.sabyrov.terrafit.entity.Subscription;
import kg.sabyrov.terrafit.enums.Status;
import kg.sabyrov.terrafit.exceptions.RequestNotFoundException;
import kg.sabyrov.terrafit.service.RequestFreezeService;
import kg.sabyrov.terrafit.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.util.List;

@Component
public class TaskSchedulingService {
    @Value("${spring.mail.username}")
    String userName;
    @Autowired
    private RequestFreezeService requestFreezeService;

    @Autowired
    private HtmlMessageSenderService htmlMessageSenderService;

    @Autowired
    private SubscriptionService subscriptionService;


    @Scheduled(cron = "00 00 12 * * *")
    public void checkAndDeActiveSubscription(){
        List<Subscription> list = getSubscriptions(Status.ACTIVE);
        for (Subscription s : list) {
            if(s.getExpirationDate().equals(LocalDate.now()) || s.getExpirationDate().isAfter(LocalDate.now()))
                s.setStatus(Status.INACTIVE);
        }
    }


    @Scheduled(cron = "00 00 09 * * *")
    public void checkAndDeFrostSubscription(){
        List<Subscription> list = getSubscriptions(Status.FROZEN);
        for (Subscription s : list) {
            if(s.getFrozenUntilDate().equals(LocalDate.now()) || s.getFrozenUntilDate().isAfter(LocalDate.now()))
                s.setStatus(Status.ACTIVE);
        }
    }


    @Scheduled(cron = "00 00 10 * * *")
    public void sendRequestsToAdminMail() throws RequestNotFoundException, MessagingException {
        List<RequestFreeze> list = requestFreezeService.getAllByStatus(Status.CONSIDERATION);
        String message = "<table border=\"2\" cellpadding=\"8\" cellspacing=\"0\" align=\"center\">\n <caption>REQUESTS</caption>\" <thead> <tr> " +
                "<th>ID</th>" +
                "<th>USER EMAIL</th>" +
                "<th>Created date</th>" +
                "<th>SubscriptionId</th>" +
                "<th>FrozenDays Quantity</th>" +
                "<th>Manager mail</th>" +
                "<th>Status</th>" +
                "</tr>" +
                "</thead><tbody>";

        for (RequestFreeze r : list) {
            message += "<tr>\n" +
                    "<td>" + r.getId()+"</td>\n" +
                    "<td>"+ r.getSubscription().getUser().getEmail()+"</td>\n" +
                    "<td>"+ r.getCreatedDate()+"</td>\n" +
                    "<td>"+ r.getSubscription().getId()+"</td>\n" +
                    "<td>"+ r.getFrozenDaysQuantity() + "</td>\n" +
                    "<td>"+ r.getManager() + "</td>\n" +
                    "<td>"+ r.getStatus() + "</td>\n" +
                    "</tr>\n";
        }
        message += "</tbody></table>";
        htmlMessageSenderService.sendHtmlEmail(message, userName);

    }

    private List<Subscription> getSubscriptions(Status status){
        return subscriptionService.getAllByStatus(status);
    }
}
