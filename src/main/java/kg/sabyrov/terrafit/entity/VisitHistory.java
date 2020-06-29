package kg.sabyrov.terrafit.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "visit_history")
public class VisitHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id", nullable = false)
    User manager;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id", referencedColumnName = "id", nullable = false)
    Visit visit;

    @Column(name = "visit_time", nullable = false)
    LocalDateTime visitTime;

    @Column(name = "session_quantity_after_visit", nullable = false)
    Integer sessionQuantityAfterVisit;

    @Column(name = "subtracted_sessions")
    Integer subtractedSessions;
}
