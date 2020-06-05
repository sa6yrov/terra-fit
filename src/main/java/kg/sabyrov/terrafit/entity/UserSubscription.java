//package kg.sabyrov.terrafit.entity;
//
//import lombok.*;
//import lombok.experimental.FieldDefaults;
//import org.springframework.data.annotation.CreatedDate;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//import java.util.Date;
//
//@Entity
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE)
//@Table(name = "user_subscriptions")
//public class UserSubscription extends BaseEntity{
//
//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
//    User user;
//
//    @ManyToOne
//    @JoinColumn(name = "subscription_id", referencedColumnName = "id", nullable = false)
//    Subscription subscription;
//
//    @Column(name = "discount_percentages")
//    Integer discountPercentages;
//
//    @Column(name = "totalPrice")
//    BigDecimal price;
//}
