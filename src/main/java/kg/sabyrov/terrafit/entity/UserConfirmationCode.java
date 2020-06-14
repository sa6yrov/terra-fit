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
@Table(name = "user_confirmation_code")
public class UserConfirmationCode extends BaseEntity {

    @Column(name = "code")
    String code;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "is_active")
    boolean isActive;
}
