package com.example.jpa_bank.service;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.entity.TransactionEntity;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.TransactionRepository;
import com.example.jpa_bank.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import static org.mockito.Mockito.*;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TransactionalServiceTest {
    @InjectMocks
    TransactionalService transactionalService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;

    @Test
    void Given_NonExistingDestinationAccount_When_Invoke_doTransaction_Then_throwRuntimeException() {
        TransactionDto transactionDto = new TransactionDto(1, 1, 3, 100);
        Mockito.when(accountRepository.existsById(transactionDto.getDestination())).thenReturn(false);
        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionalService.doTransaction(transactionDto);
        });
        Mockito.verify(accountRepository).existsById(transactionDto.getDestination());

    }
    @Test
    void Given_InsufficientFunds_When_Invoke_doTransaction_Then_throwRuntimeException() {
        AccountEntity senderAccount = new AccountEntity(2,"Ahorros",500,"2025-03-24",10);
        TransactionDto transactionDto = new TransactionDto(1, 2, 3, 1000);
        Mockito.when(accountRepository.existsById(transactionDto.getDestination())).thenReturn(true);
        Mockito.when(accountRepository.existsById(transactionDto.getOrigen())).thenReturn(true);
        Mockito.when(accountRepository.findById(transactionDto.getOrigen())).thenReturn(Optional.of(senderAccount));
        Assertions.assertThrows(RuntimeException.class, () -> {
            transactionalService.doTransaction(transactionDto);
        });
        Assertions.assertEquals(500, senderAccount.getMoney());
        Mockito.verify(accountRepository).existsById(transactionDto.getDestination());
        Mockito.verify(accountRepository).existsById(transactionDto.getOrigen());
        Mockito.verify(accountRepository, Mockito.never()).depositMoney(transactionDto.getAmount()*-1,transactionDto.getOrigen());
        Mockito.verify(accountRepository, Mockito.never()).depositMoney(transactionDto.getAmount(),transactionDto.getDestination());
        Mockito.verify(transactionRepository, Mockito.never()).save(any(TransactionEntity.class));

    }

}