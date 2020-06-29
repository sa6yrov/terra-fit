package kg.sabyrov.terrafit.controller;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.service.RequestFreezeService;
import kg.sabyrov.terrafit.service.implementation.HtmlMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.util.List;

@RestController
@RequestMapping("/api/send")
public class test {

    @Autowired
    private RequestFreezeService requestFreezeService;

    @Autowired
    private HtmlMessageSenderService htmlMessageSenderService;

    @GetMapping
    public String send() throws MessagingException {
        List<FreezeResponseDto> list = requestFreezeService.getAllByStatus();
        if(!list.isEmpty()){
            String message = "<table> <thead> <tr> " +
                    "<th>1</th>" +
                    "<th>2</th>" +
                    "<th>3</th>" +
                    "<th>4</th>" +
                    "<th>5</th>" +
                    "<th>6</th>" +
                    "</tr>" +
                    "</thead><tbody>";
            for (int i = 0; i < list.size(); i++) {
                message += "    <tr>\n" +
                        "      <td>" + list.get(i).getId()+"</td>\n" +
                        "      <td>"+ list.get(i).getUserEmail()+"</td>\n" +
                        "      <td>"+ list.get(i).getDateOfRequest()+"</td>\n" +
                        "      <td>"+ list.get(i).getDaysForFreeze() + "</td>\n" +
                        "      <td>"+ list.get(i).getStatus() + "</td>\n" +
                        "      <td>"+ list.get(i).getSubscriptionId() + "</td>\n" +
                        "    </tr>\n";
            }
            message += "</tbody></table>";
            htmlMessageSenderService.sendHtmlEmail(message);
        }
        return "Yes";
    }
}
