package kg.sabyrov.terrafit.dto.visitDto;

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
