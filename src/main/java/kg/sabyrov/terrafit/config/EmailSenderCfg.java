//package kg.sabyrov.terrafit.config;
//
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//import org.springframework.stereotype.Component;
//
//import java.util.Properties;
//
//
//@Configuration
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class EmailSenderCfg {
//    @Value("${spring.mail.host}")
//    String host;
//
//    @Value("${spring.mail.port}")
//    int port;
//
//    @Value("${spring.mail.username}")
//    String userName;
//
//    @Value("${spring.mail.password}")
//    String password;
//
//    @Bean
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//
//        mailSender.setHost(host);
//        mailSender.setPort(port);
//
//        mailSender.setUsername(userName);
//        mailSender.setPassword(password);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "false");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }

//}
