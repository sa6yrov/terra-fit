package kg.sabyrov.terrafit.dto.subscriptionDto;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionResponseModel {
    String trainingName;
    Integer sessionQuantity;
    BigDecimal price;
    Integer discountPercentages;
    BigDecimal totalAmount;
    Status status;
}
