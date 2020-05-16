package kg.sabyrov.terrafit.models.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtTokenResponse implements Serializable {
    static final long serialVersionUID = 8317676219297719109L;

    String token;

    public String getToken() {
        return token;
    }
}
