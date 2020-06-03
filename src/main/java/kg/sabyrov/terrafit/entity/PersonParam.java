package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "person_params")
public class PersonParam extends BaseEntity{

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "height")
    Float height;

    @Column(name = "weight")
    Float weight;

    @Column(name = "neck_girth")
    Float neckGirth;

    @Column(name = "shoulder_width")
    Float shoulderWidth;

    @Column(name = "waste_girth")
    Float wasteGirth;

    @Column(name = "hip_girth")
    Float hipGirth;

    @Column(name = "bicep_girth")
    Float bicepGirth;

    @Column(name = "chest_girth")
    Float chestGirth;

    @Column(name = "note_girth")
    Float noteGirth;
}
