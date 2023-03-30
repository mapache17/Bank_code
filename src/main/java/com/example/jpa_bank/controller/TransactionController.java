package com.example.jpa_bank.controller;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.service.TransactionalService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class TransactionController {
    private TransactionalService transactionalService;
    @PostMapping(path = "/transaction/money-sender")
    public String doTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionalService.doTransaction(transactionDto);
    }
}
