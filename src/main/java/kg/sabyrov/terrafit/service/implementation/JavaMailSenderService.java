package kg.sabyrov.terrafit.service.implementation;


import kg.sabyrov.terrafit.entity.User;
import kg.sabyrov.terrafit.entity.UserConfirmationCode;
import kg.sabyrov.terrafit.exceptions.MailSendException;
import kg.sabyrov.terrafit.service.UserConfirmationCodeService;
import kg.sabyrov.terrafit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class JavaMailSenderService {
    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;

    @Value("${spring.mail.username}")
    String userName;

    @Value("${spring.mail.password}")
    String password;

    @Autowired
    private UserConfirmationCodeService userConfirmationCodeService;

    @Autowired
    private UserService userService;

    public String sendMessage(String email) throws Exception {
        User user = userService.findByEmail(email);
        if(user.getIsActive() == 1) throw new MailSendException("User is active");
        try {
            UserConfirmationCode userConfirmationCode = userConfirmationCodeService.create(email);

            //Create mail subject
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("terragymkg@gmail.com");
            message.setTo(email);
            message.setSubject("Recovering your account");
            message.setText(userConfirmationCode.getCode());

            getJavaMailSender().send(message);
            return "Check your email for activation your account";
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(userName);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }
}
