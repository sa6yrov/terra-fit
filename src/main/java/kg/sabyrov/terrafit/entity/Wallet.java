package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "wallets")
public class Wallet extends BaseEntity{


    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;

    @Column(name = "requisite")
    String requisite;

    @Column(name = "balance")
    BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    Status status;

}
