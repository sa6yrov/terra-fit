package kg.sabyrov.terrafit.dto.userDto;

import io.swagger.annotations.ApiModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@ApiModel(description = "Model to create a new USER")
public class UserDto {
    @Email(regexp=".@.\\..*", message = "Email should be valid")
    String email;
    String password;
    String phoneNumber;
    String name;
    String surname;
    LocalDate birthDate;
    String gender;
}
