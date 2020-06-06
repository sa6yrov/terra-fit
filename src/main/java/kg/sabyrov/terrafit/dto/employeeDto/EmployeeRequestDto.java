package kg.sabyrov.terrafit.dto.employeeDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.FieldResult;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class EmployeeRequestDto {
    String email;
    String position;
}