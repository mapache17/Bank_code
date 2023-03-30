package com.example.jpa_bank.controller;
import com.example.jpa_bank.controller.dto.*;
import com.example.jpa_bank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AccountController {
    private AccountService accountService;
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
}
