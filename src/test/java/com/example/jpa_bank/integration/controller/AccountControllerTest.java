package com.example.jpa_bank.integration.controller;
import com.example.jpa_bank.AbstractTest;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class AccountControllerTest extends AbstractTest {
    private static final String PATH_CREATE_ACCOUNT = "/account/savings-account";
    private static final String PATH_CREATE_USER = "/user/savings-user";
    private static final String PATH_CHECK_BALANCE = "/account/check-balance/";
    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    void Given_AnExistingUser_When_Invoke_createAccount_Then_CreateANewAccount()
    {
        AccountDto accountDto = new AccountDto(1,"Ahorro",0,"2025-03-24",1);
        UserDto userDto = new UserDto(1,"Jhoan","Ome","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        ResponseEntity<AccountEntity> accountEntityResponseEntity = restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);
        assertEquals(new AccountEntity(1,"Ahorro",0,"2025-03-24",1), accountEntityResponseEntity.getBody());
    }
    @Test
    void Given_AnExistingUserWithMoreThanFourAccounts_When_Invoke_createAccount_Then_InternalServerError()
    {
        AccountDto accountDto=null;
        UserDto userDto = new UserDto(1,"Jhoan","Ome","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        for (int i=0;i<=4;i++)
        {
            accountDto = new AccountDto(i,"Ahorro",0,"2025-03-24",userDto.getDocument());
            restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);
        }
        ResponseEntity<AccountEntity> accountEntityResponseEntity= restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);
        assertEquals(HttpStatusCode.valueOf(500), accountEntityResponseEntity.getStatusCode());
    }
    @Test
    void Given_AnUserNOExist_When_Invoke_createAccount_Then_InternalServerError()
    {
        AccountDto accountDto = new AccountDto(1,"Ahorro",0,"2025-03-24",2);
        ResponseEntity<AccountEntity> accountEntityResponseEntity= restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);
        assertEquals(HttpStatusCode.valueOf(500), accountEntityResponseEntity.getStatusCode());
    }
    @Test
    void Given_AnExistingIdAccount_When_Invoke_checkBalance_Then_ConsultBalance()
    {
        UserDto userDto = new UserDto(2,"Jhoan","Ome","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        AccountDto accountDto = new AccountDto(1,"Ahorro",20,"2025-03-24",userDto.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);
        ResponseEntity<AccountEntity> accountEntityResponseEntity= restTemplate.getForEntity(PATH_CHECK_BALANCE+accountDto.getId(), AccountEntity.class);
        assertEquals(new AccountEntity(1,"Ahorro",20,"2025-03-24",2),accountEntityResponseEntity.getBody());
    }
    @Test
    void Given_AnIdAccountNOExist_When_Invoke_checkBalance_Then_InternalServerError()
    {
        ResponseEntity<AccountEntity> accountEntityResponseEntity= restTemplate.getForEntity(PATH_CHECK_BALANCE+1, AccountEntity.class);
        assertEquals(HttpStatusCode.valueOf(500),accountEntityResponseEntity.getStatusCode());
    }
}
