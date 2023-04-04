package com.example.jpa_bank.service;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.entity.TransactionEntity;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class TransactionalServiceTest {
    @InjectMocks
    TransactionalService transactionalService;
    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private AccountRepository accountRepository;

    @Test
    void Given_NonExistingOriginAccount_When_Invoke_doTransaction_Then_RuntimeException() {
        TransactionDto transactionDto=new TransactionDto(1,1,1,100);
        Mockito.when(accountRepository.existsById(transactionDto.getOrigen())).thenReturn(false);
        Assertions.assertThrows(RuntimeException.class,() -> {
            transactionalService.doTransaction(transactionDto);
        });
        Mockito.verify(accountRepository).existsById(transactionDto.getOrigen());
    }
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
    @Test
    void Given_ExistingOriginAccountWithExistingDestinationAccountWithEnoughMoney_When_Invoke_doTransaction_Then_Return_TransactionEntity(){
        AccountEntity senderAccount=(new AccountEntity(  1,"Ahorro",100,"2025-03-24",1));
        AccountEntity destinationAccount=(new AccountEntity(  2,"Ahorro",0,"2025-03-24",2));
        TransactionDto transactionDto = new TransactionDto(1, 1, 2, 100);

        Mockito.when(accountRepository.existsById(transactionDto.getDestination())).thenReturn(true);
        Mockito.when(accountRepository.existsById(transactionDto.getOrigen())).thenReturn(true);
        Mockito.when(accountRepository.findById(transactionDto.getOrigen())).thenReturn(Optional.of(senderAccount));
        Mockito.when(transactionalService.doTransaction(transactionDto)).thenReturn(new TransactionEntity(1,1,2,100));
        TransactionEntity transactionEntity=transactionalService.doTransaction(transactionDto);

        Assertions.assertEquals(new TransactionEntity(1,1,2,100), transactionEntity);
        Assertions.assertEquals(100, senderAccount.getMoney());
        Assertions.assertEquals(0, destinationAccount.getMoney());
        Mockito.verify(accountRepository, Mockito.atLeast(1)).existsById(transactionDto.getDestination());
        Mockito.verify(accountRepository,Mockito.atLeast(1)).existsById(transactionDto.getOrigen());
        Mockito.verify(accountRepository,Mockito.atLeast(1)).depositMoney(transactionDto.getAmount()*-1,transactionDto.getOrigen());
        Mockito.verify(accountRepository,Mockito.atLeast(1)).depositMoney(transactionDto.getAmount(),transactionDto.getDestination());
        Mockito.verify(transactionRepository).save(any(TransactionEntity.class));
    }
}