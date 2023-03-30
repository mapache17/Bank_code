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
    TransactionRepository transactionRepository;
    AccountRepository accountRepository;

    public String doTransaction(TransactionDto transactionDto)
    {
        if (this.accountRepository.existsById(transactionDto.getDestination())&&this.accountRepository.existsById(transactionDto.getOrigen()))
        {
            AccountEntity senderAccount = accountRepository.findById(transactionDto.getOrigen()).orElse(new AccountEntity());

            if (senderAccount.getMoney()>=transactionDto.getAmount())
            {
                accountRepository.depositMoney(transactionDto.getAmount(),transactionDto.getDestination());
                accountRepository.depositMoney(-1*transactionDto.getAmount(),transactionDto.getOrigen());
                this.inserTable(transactionDto);
                return "successful transfer: "+transactionDto;
            }
            else
            {
                return "Insufficient balance";
            }
        }
        else
        {
            return "The account doesn't exist";
        }
    }
    private void inserTable(TransactionDto transactionDto)
    {
        transactionRepository.save(new TransactionEntity(transactionDto.getId(), transactionDto.getOrigen(),transactionDto.getDestination(),transactionDto.getAmount()));
    }
}
