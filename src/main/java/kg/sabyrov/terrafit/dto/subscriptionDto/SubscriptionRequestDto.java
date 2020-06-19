package kg.sabyrov.terrafit.dto.subscriptionDto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionRequestDto {
    Long trainingGroupId;
    Integer sessionQuantity;
    String promoCode;
}
