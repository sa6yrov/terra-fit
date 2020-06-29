package kg.sabyrov.terrafit;

import kg.sabyrov.terrafit.dto.requestForFreezeDto.FreezeResponseDto;
import kg.sabyrov.terrafit.service.RequestFreezeService;
import kg.sabyrov.terrafit.service.implementation.HtmlMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.mail.MessagingException;
import java.util.List;

@SpringBootApplication
public class TerrafitApplication {

	@Autowired
	private static RequestFreezeService requestFreezeService;

	@Autowired
	private static HtmlMessageSenderService htmlMessageSenderService;

	public static void main(String[] args) throws MessagingException {
		SpringApplication.run(TerrafitApplication.class, args);

	}
}
