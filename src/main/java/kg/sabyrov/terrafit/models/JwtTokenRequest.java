package kg.sabyrov.terrafit.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class JwtTokenRequest implements Serializable {
    static final long serialVersionUID = -5616176897013108345L;

    String email;
    String password;

}
