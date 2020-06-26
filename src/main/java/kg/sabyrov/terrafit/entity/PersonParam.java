package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "person_params")
public class PersonParam extends BaseEntity{


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

    @Column(name = "waist_girth")
    Float waistGirth;

    @Column(name = "hip_girth")
    Float hipGirth;

    @Column(name = "bicep_girth")
    Float bicepGirth;

    @Column(name = "chest_girth")
    Float chestGirth;
}
