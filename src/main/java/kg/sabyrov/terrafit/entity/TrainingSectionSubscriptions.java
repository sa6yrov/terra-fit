package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level =  AccessLevel.PRIVATE)
@Table(name = "training_section_subscriptions_type")
public class TrainingSectionSubscriptions extends BaseEntity{

    @ManyToOne
    TrainingSection trainingSection;


}
