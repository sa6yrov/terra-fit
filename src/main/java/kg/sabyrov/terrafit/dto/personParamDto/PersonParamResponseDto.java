package kg.sabyrov.terrafit.dto.personParamDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonParamResponseDto {
    Float height;
    Float weight;
    Float neckGirth;
    Float shoulderWidth;
    Float waistGirth;
    Float hipGirth;
    Float bicepGirth;
    Float chestGirth;
    LocalDateTime createdDate;
}
