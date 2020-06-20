package kg.sabyrov.terrafit.dto.visitDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitResponseDto {
    String email;
    String name;
    String surname;
    String trainingGroupName;
    Long subscriptionId;
    Integer sessionQuantity;
    LocalDateTime visitTime;
}
