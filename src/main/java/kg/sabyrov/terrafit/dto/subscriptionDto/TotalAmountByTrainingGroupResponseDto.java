package kg.sabyrov.terrafit.dto.subscriptionDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TotalAmountByTrainingGroupResponseDto {
    String trainingGroupName;
    BigDecimal amount;
}
