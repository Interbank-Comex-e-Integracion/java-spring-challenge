package ibk.challenge.transaction.dto;

import ibk.challenge.transaction.definition.TransactionStatusDefinition;
import ibk.challenge.transaction.definition.TransactionTypeDefinition;
import ibk.challenge.transaction.entity.Transaction;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTransactionInfoResponse {
    private String transactionExternalId;
    private TransactionType transactionType;
    private TransactionStatus transactionStatus;
    private Long value;
    private Instant createdAt;

    @Data
    public static class TransactionType
    {
        private String name;
    }

    @Data
    public static class TransactionStatus
    {
        private String name;
    }

    public static TransactionType mapTransactionType(int type) {
        TransactionType transactionType = new TransactionType();

        switch (type) {
            case TransactionTypeDefinition.TRANSFER_TYPE:
                transactionType.setName(TransactionTypeDefinition.TRANSFER_TYPE_LABEL); break;
            case TransactionTypeDefinition.PAYMENT_TYPE:
                transactionType.setName(TransactionTypeDefinition.PAYMENT_TYPE_LABEL); break;
            default:
                transactionType.setName("Unknown");
        }
        return transactionType;
    }

    public static TransactionStatus mapTransactionStatus(int status) {
        TransactionStatus transactionStatus = new TransactionStatus();

        switch (status) {
            case TransactionStatusDefinition.PENDING_STATUS:
                transactionStatus.setName(TransactionStatusDefinition.PENDING_STATUS_LABEL); break;
            case TransactionStatusDefinition.APPROVED_STATUS:
                transactionStatus.setName(TransactionStatusDefinition.APPROVED_STATUS_LABEL); break;
            case TransactionStatusDefinition.DENIED_STATUS:
                transactionStatus.setName(TransactionStatusDefinition.DENIED_STATUS_LABEL); break;
            default:
                transactionStatus.setName("Unknown");
        }
        return transactionStatus;
    }

}


