package kg.sabyrov.terrafit.service.implementation;

import kg.sabyrov.terrafit.config.EmailSenderCfg;
import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.exceptions.UserNotFoundException;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class JavaMailSenderService {
    @Autowired
    private JavaMailSender javaMailSender;

    private EmailSenderCfg emailSenderCfg;

    @Autowired
    private UserConfirmationCodeService userConfirmationCodeService;

    public String sendMessage(String email) throws UserNotFoundException {
        UserConfirmationCode userConfirmationCode = userConfirmationCodeService.create(email);

        //Create mail sender
//        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
//        javaMailSender.setHost(this.emailSenderCfg.getHost());
//        javaMailSender.setPort(this.emailSenderCfg.getPort());
//        javaMailSender.setUsername(this.emailSenderCfg.getUserName());
//        javaMailSender.setPassword(this.emailSenderCfg.getPassword());

        //Create mail subject
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("terragymkg@gmail.com");
        message.setTo(email);
        message.setSubject("Recovering your account");
        message.setText(userConfirmationCode.getCode());

        javaMailSender.send(message);
        return "Check your email for activation your account";
    }
}
