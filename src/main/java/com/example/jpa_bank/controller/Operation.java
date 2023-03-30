package com.example.jpa_bank.controller;

import com.example.jpa_bank.controller.dto.*;
import com.example.jpa_bank.service.AccountService;
import com.example.jpa_bank.service.TransactionalService;
import com.example.jpa_bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class Operation {
    private AccountService accountService;
    private UserService userService;
    private TransactionalService transactionalService;
    @PostMapping(path = "/account/savings-account")
    public String createAccount(@RequestBody AccountDto accountDto) {
        return accountService.insertAccount(accountDto);
    }
    @PutMapping(path = "/account/deposit-money")
    public String depositMoney(@RequestBody DepositMoneyUserDto depositMoneyUserDto) {
        return accountService.depositMoney(depositMoneyUserDto);
    }
    @GetMapping(path = "/account/check-balance/{idAccount}")
    public String checkBalance(@PathVariable int idAccount) {
        return accountService.checkBalance(idAccount);
    }
    @PostMapping(path = "/user/savings-user")
    public String createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @GetMapping(path = "/account/check-accounts/{idDocument}")
    public String getAllAccounts(@PathVariable int idDocument) {
        return userService.consultAccounts(idDocument);
    }
    @PostMapping(path = "/transaction/money-sender")
    public String doTransaction(@RequestBody TransactionDto transactionDto) {
        return transactionalService.doTransaction(transactionDto);
    }
}
