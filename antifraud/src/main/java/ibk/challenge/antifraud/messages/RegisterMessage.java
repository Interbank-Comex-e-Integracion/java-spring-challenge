package ibk.challenge.antifraud.messages;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterMessage {
    private String transactionId;
    private Long value;
}
