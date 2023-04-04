package com.example.jpa_bank.integration.controller;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.entity.AccountEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest
public class AccountControllerTest {
    private static final String PATH_CREATE_ACCOUNT = "/account/savings-account";
    @Autowired
    private TestRestTemplate restTemplate;
    void Given_AUserWithMoreThanFourRegisteredAccounts_When_Invoke_createAccount_Then_throwRuntimeException()
    {
        AccountDto accountDto = new AccountDto(5,"Ahorro",0,"2025-03-24",1);
        ResponseEntity<AccountEntity> accountEntityResponseEntity = restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);

    }
}
