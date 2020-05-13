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
@Table(name = "training_sections")
public class TrainingSection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "facility_id", referencedColumnName = "id")
    Facility facility;

    @Column(name = "name")
    String name;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employees;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;
}
