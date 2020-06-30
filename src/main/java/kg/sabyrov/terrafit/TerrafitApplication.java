package kg.sabyrov.terrafit;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.service.RequestFreezeService;
import kg.sabyrov.terrafit.service.implementation.HtmlMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.mail.MessagingException;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class TerrafitApplication {

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(TerrafitApplication.class, args);

	}
}
