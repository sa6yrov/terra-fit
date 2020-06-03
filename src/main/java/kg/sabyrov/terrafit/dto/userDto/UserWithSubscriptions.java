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
public class UserWithSubscriptions {
    String email;
    String name;
    String surname;
    String phoneNumber;
    LocalDate birthDate;
    String gender;
    List<SubscriptionResponseModel> subscriptionResponseModels;
}
