package kg.sabyrov.terrafit.config;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmailSenderCfg {
    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;

    @Value("${spring.mail.username}")
    String userName;

    @Value("${spring.mail.password}")
    String password;

}
