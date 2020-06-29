package kg.sabyrov.terrafit.dto.personParamDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonParamRequestDto {
    Float height;
    Float weight;
    Float neckGirth;
    Float shoulderWidth;
    Float waistGirth;
    Float hipGirth;
    Float bicepGirth;
    Float chestGirth;
}
