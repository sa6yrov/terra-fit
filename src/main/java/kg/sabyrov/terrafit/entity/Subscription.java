package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id", nullable = false)
    Facility facility;

    @ManyToOne
    @JoinColumn(name = "training_section_id", referencedColumnName = "id", nullable = false)
    TrainingSection trainingSection;

    @Column(name = "session_quantity", nullable = false)
    Integer sessionQuantity;

    @Column(name = "price", nullable = false)
    Integer price;

    @Column(name = "code", nullable = false, unique = true)
    String code;

    @Column(name = "discount_percentages")
    Integer discountPercentages;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;
}
