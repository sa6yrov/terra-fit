package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subscriptions")
public class Subscription extends BaseEntity{


    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;

    @ManyToOne
    @JoinColumn(name = "training_section_id", referencedColumnName = "id", nullable = false)
    TrainingGroup trainingGroup;

    @Column(name = "session_quantity", nullable = false)
    Integer sessionQuantity;

    @Column(name = "discount_percentages")
    Integer discountPercentages;

    @Column(name = "total_amount")
    BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;

    @Column(name = "expiration_date")
    LocalDate expirationDate;

    @Column(name = "frozen_until_date")
    LocalDate frozenUntilDate;


}
