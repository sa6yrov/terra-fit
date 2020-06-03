package kg.sabyrov.terrafit.dto.userDto;

import kg.sabyrov.terrafit.dto.subscriptionDto.SubscriptionResponseModel;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSubscriptionModel {
    String email;
    String name;
    String surname;
    String phoneNumber;
    LocalDate birthDate;
    String gender;
    SubscriptionResponseModel subscriptionResponseModel;
}
