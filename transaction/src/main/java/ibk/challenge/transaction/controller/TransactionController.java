package ibk.challenge.transaction.controller;

import ibk.challenge.transaction.dto.CreateTransactionRequest;
import ibk.challenge.transaction.dto.CreateTransactionResponse;
import ibk.challenge.transaction.dto.GetTransactionInfoResponse;
import ibk.challenge.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transactions")
    public CreateTransactionResponse createTransaction(@RequestBody CreateTransactionRequest transaction) {
        return transactionService.createTransaction(transaction);
    }

    @GetMapping("/transactions/{txnId}")
    public GetTransactionInfoResponse getTransaction(@PathVariable String txnId) {
        return transactionService.getTransactionByTransactionId(UUID.fromString(txnId));
    }
}
