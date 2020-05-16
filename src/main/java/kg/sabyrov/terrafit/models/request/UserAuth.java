package kg.sabyrov.terrafit.models.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UserAuth implements Serializable {
    static final long serialVersionUID = -5616176897013108345L;

    String email;
    String password;

}
