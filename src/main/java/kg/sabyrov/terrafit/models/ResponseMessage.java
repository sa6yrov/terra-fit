package kg.sabyrov.terrafit.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseMessage {
    String message;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
