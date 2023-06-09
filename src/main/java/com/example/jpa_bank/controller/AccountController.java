package com.example.jpa_bank.controller;
import com.example.jpa_bank.controller.dto.*;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class AccountController {
    private AccountService accountService;
    @PostMapping(path = "/account/savings-account")
    public AccountEntity createAccount(@RequestBody AccountDto accountDto) {
        return accountService.insertAccount(accountDto);
    }

    @GetMapping(path = "/account/check-balance/{idAccount}")
    public AccountEntity checkBalance(@PathVariable int idAccount) {
        return accountService.checkBalance(idAccount);
    }
}
