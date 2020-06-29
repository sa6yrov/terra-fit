package kg.sabyrov.terrafit.dto.subscriptionDto;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscriptionResponseDto {
    String trainingName;
    Long subscriptionId;
    String userEmail;
    Integer sessionQuantity;
    BigDecimal price;
    Integer discountPercentages;
    BigDecimal totalAmount;
    Status status;
    LocalDateTime dateOfPurchase;
}
