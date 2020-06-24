package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "visits")
public class Visit extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @ManyToOne
    @JoinColumn(name = "subscription_id", referencedColumnName = "id")
    Subscription subscription;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    User manager;

}
