package ibk.challenge.transaction.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @UuidGenerator
    private UUID transactionId;
    private String accountExternalIdDebit;
    private String accountExternalIdCredit;
    private Integer transferTypeId;
    private Integer transactionStatus;
    private Long value;

    @CreatedDate
    private Instant createdDate;
    @LastModifiedDate
    private Instant lastModifiedDate;

}
