package ibk.challenge.transaction.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransactionRequest {
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Integer transferTypeId;
    private Long value;
}
