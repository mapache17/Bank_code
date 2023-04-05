package com.example.jpa_bank.controller;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.entity.TransactionEntity;
import com.example.jpa_bank.service.TransactionalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TransactionController {
    private TransactionalService transactionalService;
    @PostMapping(path = "/transaction/transfer-money")
    public TransactionEntity doTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionalService.doTransaction(transactionDto);
    }
}
