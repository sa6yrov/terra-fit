package kg.sabyrov.terrafit.entity;

import kg.sabyrov.terrafit.enums.Currency;
import kg.sabyrov.terrafit.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "payments")
public class Payment extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "wallet_from_id", referencedColumnName = "id", nullable = false)
    Wallet walletFrom;

    @ManyToOne
    @JoinColumn(name = "wallet_to_id", referencedColumnName = "id", nullable = false)
    Wallet walletTo;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    Currency currency;




}
