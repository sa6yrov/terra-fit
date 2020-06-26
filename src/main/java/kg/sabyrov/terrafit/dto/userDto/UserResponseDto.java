package kg.sabyrov.terrafit.dto.userDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponseDto {
    Long id;
    String email;
    String phoneNumber;
    String name;
    String surname;
    LocalDate birthDate;
    String gender;
}
