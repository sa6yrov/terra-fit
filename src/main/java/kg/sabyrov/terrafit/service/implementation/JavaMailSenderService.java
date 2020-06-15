package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class JavaMailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserConfirmationCodeService userConfirmationCodeService;

    public String sendMessage(String email) throws UserNotFoundException {
        UserConfirmationCode userConfirmationCode = userConfirmationCodeService.create(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Recovering your account");
        message.setText(userConfirmationCode.getCode());
        javaMailSender.send(message);
        return "Check your email for activation your account";
    }
}
