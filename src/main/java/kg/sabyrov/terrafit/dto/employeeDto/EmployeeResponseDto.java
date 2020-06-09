package kg.sabyrov.terrafit.dto.employeeDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeResponseDto {
    String email;
    String name;
    String surname;
    LocalDate birthDate;
    String phoneNumber;
    String gender;
}
