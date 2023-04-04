package com.example.jpa_bank.service;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.DepositMoneyUserDto;
import com.example.jpa_bank.controller.dto.TransactionDto;
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
import java.util.Optional;
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
}