package kg.sabyrov.terrafit.dto.visitDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VisitHistoryResponseDto {
    String managerEmail;
    String visitorEmail;
    LocalDateTime visitTime;
    Long subscriptionId;
    Integer sessionQuantityAfterVisit;
    Integer subtractedSessions;
}
