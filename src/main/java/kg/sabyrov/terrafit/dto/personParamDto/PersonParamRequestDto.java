package kg.sabyrov.terrafit.dto.personParamDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonParamRequestDto {
    String email;
    Float height;
    Float weight;
    Float neckGirth;
    Float shoulderWidth;
    Float waistGirth;
    Float hipGirth;
    Float bicepGirth;
    Float chestGirth;
    Float noteGirth;
}
