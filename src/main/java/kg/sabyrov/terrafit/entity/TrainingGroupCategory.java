package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "training_group_category")
public class TrainingGroupCategory extends BaseEntity{

    @Column(name = "name")
    String name;
}
