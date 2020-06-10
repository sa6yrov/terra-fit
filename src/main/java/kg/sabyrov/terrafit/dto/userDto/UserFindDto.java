package kg.sabyrov.terrafit.dto.userDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserFindDto {
    String surname;
    String name;
}
