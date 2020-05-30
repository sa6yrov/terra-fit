package kg.sabyrov.terrafit.dto.userDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserModel {
    String email;
    String password;
    String phoneNumber;
    String name;
    String surname;
    Date birthDate;
    String gender;
}
