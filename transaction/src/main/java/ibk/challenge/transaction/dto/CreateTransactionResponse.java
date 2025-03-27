package ibk.challenge.transaction.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionResponse {
    private String transactionExternalId;
}
