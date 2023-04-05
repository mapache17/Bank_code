package com.example.jpa_bank.service;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.TransactionEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TransactionalService {
    private TransactionRepository transactionRepository;
    private AccountRepository accountRepository;
    public TransactionEntity doTransaction(TransactionDto transactionDto) {
        if (!this.accountRepository.existsById(transactionDto.getDestination())) {
            throw new RuntimeException("Verifique la cuenta de destino.");
        }
        if (!this.accountRepository.existsById(transactionDto.getOrigen())) {
            throw new RuntimeException("Verifique la cuenta de origen.");
        }
        AccountEntity senderAccount = accountRepository.findById(transactionDto.getOrigen()).orElse(new AccountEntity());
        if (senderAccount.getMoney()<transactionDto.getAmount()) {
            throw new RuntimeException("Fondos insuficientes.");
        }
        accountRepository.depositMoney(transactionDto.getAmount(),transactionDto.getDestination());
        accountRepository.depositMoney(-1*transactionDto.getAmount(),transactionDto.getOrigen());
        return transactionRepository.save(new TransactionEntity(transactionDto.getId(), transactionDto.getOrigen(),transactionDto.getDestination(),transactionDto.getAmount()));
    }
}
