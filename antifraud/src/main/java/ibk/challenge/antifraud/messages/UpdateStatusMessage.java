package ibk.challenge.antifraud.messages;


import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusMessage {
    private String transactionId;
    private Integer transactionalStatus;
}
