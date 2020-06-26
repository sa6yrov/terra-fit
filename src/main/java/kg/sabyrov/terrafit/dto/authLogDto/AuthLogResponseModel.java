package kg.sabyrov.terrafit.dto.authLogDto;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthLogResponseModel {
    String userEmail;
    Status status;
    LocalDateTime createdDate;
}
