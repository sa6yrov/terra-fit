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
@Table(name = "facilities")
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "location", nullable = false)
    String location;

    @Column(name = "website", nullable = false)
    String website;

    @Column(name = "contact_number", nullable = false)
    String contactNumber;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;
}
