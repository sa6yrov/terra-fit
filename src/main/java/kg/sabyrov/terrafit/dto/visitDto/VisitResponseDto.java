package kg.sabyrov.terrafit.dto.visitDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitResponseDto {
    String email;
    String name;
    String surname;
    Long subscriptionId;
    String trainingGroup;
}
