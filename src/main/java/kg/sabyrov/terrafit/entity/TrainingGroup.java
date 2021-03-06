package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "training_groups")
public class TrainingGroup extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "training_group_category_id", referencedColumnName = "id")
    TrainingGroupCategory trainingGroupCategory;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;

    @Column(name = "subscription_price")
    BigDecimal subscriptionPrice;

    @Column(name = "training_time")
    String trainingTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;
}
