package com.example.jpa_bank.controller;

import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.DepositMoneyUserDto;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.service.AccountService;
import com.example.jpa_bank.service.TransactionalService;
import com.example.jpa_bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class Operation {
    private AccountService account;
    private UserService userService;
    private TransactionalService transactionalService;
    @PostMapping(path = "/account/savings-account")
    public String createAccount(@RequestBody AccountDto accountDto)
    {
        return account.insertAccount(accountDto);
    }
    @PutMapping(path = "/account/deposit-money")
    public String depositMoney(@RequestBody DepositMoneyUserDto depositMoneyUserDto)
    {
        return account.depositMoney(depositMoneyUserDto);
    }
    @GetMapping(path = "/account/check-balance/{idAccount}")
    public String checkBalancene(@PathVariable int idAccount)
    {
        return account.checkBalance(idAccount);
    }
    @PostMapping(path = "/user/savings-user")
    public String createUser(@RequestBody UserDto userDto)
    {
        return userService.createUser(userDto);
    }
    @GetMapping(path = "/account/check-accounts/{idDocument}")
    public String getAllAccounts(@PathVariable int idDocument)
    {
        return userService.consultAccounts(idDocument);
    }

    @PostMapping(path = "/transaction/money-sender")
    public String doTransaction(@RequestBody TransactionDto transactionDto)
    {
        return transactionalService.doTransaction(transactionDto);
    }

}
