package ibk.challenge.transaction.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStatusMessage {
    private String transactionId;
    private Integer transactionalStatus;
}
