package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Status;
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
@Table(name = "employees")
public class Employee extends BaseEntity{


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    User user;


    @Column(name = "position", nullable = false)
    String position;


    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;
}
