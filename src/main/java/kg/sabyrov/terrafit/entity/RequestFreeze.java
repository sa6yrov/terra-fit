package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "requests_for_freeze")
public class RequestFreeze extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id", nullable = false)
    Subscription subscription;

    @Column(name = "frozen_days_quantity", nullable = false)
    Long frozenDaysQuantity;

    @Column(name = "status")
    Status status;
    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    User manager;

    @Column(name = "notify")
    Integer notify;
}
