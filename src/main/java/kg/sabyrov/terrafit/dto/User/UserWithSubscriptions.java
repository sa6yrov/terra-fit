package kg.sabyrov.terrafit.dto.User;

import kg.sabyrov.terrafit.entity.Subscription;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

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
    Date birthDate;
    String gender;
    List<>
}
