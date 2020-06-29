package kg.sabyrov.terrafit.dto.requestForFreezeDto;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FreezeResponseDto {
    Long id;
    String userEmail;
    Long subscriptionId;
    Long daysForFreeze;
    LocalDateTime dateOfRequest;
    Status status;
}
