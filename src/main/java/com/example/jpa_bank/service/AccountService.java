package com.example.jpa_bank.service;

import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.DepositMoneyUserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;
    public String insertAccount(AccountDto accountDto){
        if(this.userRepository.existsById(accountDto.getUser()) && accountRepository.getAllAccounts(accountDto.getUser()).size()<=4) {
            accountRepository.save(new AccountEntity(accountDto.getId(),accountDto.getType(),accountDto.getMoney(),accountDto.getDateCreated(),accountDto.getUser()));
            return "The account was created";
        }
        else{
            return "Failed to create a account";
        }
    }
    public String depositMoney(DepositMoneyUserDto depositMoneyUserDto) {
        try{
            accountRepository.depositMoney(depositMoneyUserDto.getMoneyAmount(), depositMoneyUserDto.getAccountNumber());
            return "Your account has been recharged ";
        }
        catch (Exception e){
            System.out.println(e);
            return "Your account has NOT been recharged";
        }
    }
    public String checkBalance(int accountNumber){
        AccountEntity actualAccount = accountRepository.findById(accountNumber).orElse(new AccountEntity());
        UserEntity actualUser = userRepository.findById(actualAccount.getUser()).orElse(new UserEntity());
        return "The user: " +actualUser.getName() +" Has $"+actualAccount.getMoney()+" with account number: "+accountNumber;
    }
}