package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "promo_codes")
public class PromoCode extends BaseEntity{

    @Column(name = "name")
    String name;

    @Column(name = "discount_percentages")
    Integer discountPercentages;
}
