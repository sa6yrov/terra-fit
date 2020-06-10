package kg.sabyrov.terrafit.dto.trainingsectionDto;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingGroupRequestDto {
    String name;
    String employeeEmail;
    BigDecimal subscriptionPrice;
    Status status;
}
