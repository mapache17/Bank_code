package com.example.jpa_bank.integration.controller;
import com.example.jpa_bank.AbstractTest;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import com.example.jpa_bank.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserControllerTest extends AbstractTest{
    private static final String PATH_CREATE_USER = "/user/savings-user";
    private static final String PATH_CREATE_ACCOUNT = "/account/savings-account";
    private static final String PATH_USER_ACCOUNTS = "/user/check-accounts/";
    private static final String PATH_ALL_USERS = "/user/all-Users";

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    void Given_UserDataInDto_When_Invoke_createUser_Then_CreateNewUser() {
        UserDto userDto = new UserDto(1,"Majo","Casanova","2025-03-24");
        ResponseEntity<UserEntity> userEntityResponseEntity= restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        assertEquals(new UserEntity(1,"Majo","Casanova","2025-03-24"), userEntityResponseEntity.getBody());
    }

    @Test
    void Given_RequestUserAccountsExistingUser_When_Invoke_getAllAccounts_Then_ConsultAccounts() {
        UserDto userDto = new UserDto(99,"Pepe","Pan","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        AccountDto accountDto1 = new AccountDto(1,"Ahorro",100,"2025-03-24",userDto.getDocument());
        AccountDto accountDto2 = new AccountDto(2,"Corriente",200,"2025-03-24",userDto.getDocument());
        AccountDto accountDto3 = new AccountDto(3,"Ahorro",300,"2025-03-24",userDto.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto1, AccountEntity.class);
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto2, AccountEntity.class);
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto3, AccountEntity.class);
        List<AccountEntity> expectedAcc = new ArrayList<>();
        expectedAcc.add(new AccountEntity(accountDto1.getId(),accountDto1.getType(),accountDto1.getMoney(),accountDto1.getDateCreated(),accountDto1.getUser()));
        expectedAcc.add(new AccountEntity(accountDto2.getId(),accountDto2.getType(),accountDto2.getMoney(),accountDto2.getDateCreated(),accountDto2.getUser()));
        expectedAcc.add(new AccountEntity(accountDto3.getId(),accountDto3.getType(),accountDto3.getMoney(),accountDto3.getDateCreated(),accountDto3.getUser()));
        List<AccountEntity> actualAcc = new ArrayList<>();
        ResponseEntity<List<AccountEntity>> response = restTemplate.exchange(PATH_USER_ACCOUNTS+userDto.getDocument(), HttpMethod.GET, null, new ParameterizedTypeReference<List<AccountEntity>>() {});
        List<AccountEntity> actualAccounts = response.getBody();
        actualAcc.add(actualAccounts.get(actualAccounts.size()-3));
        actualAcc.add(actualAccounts.get(actualAccounts.size()-2));
        actualAcc.add(actualAccounts.get(actualAccounts.size()-1));
        assertEquals(expectedAcc,actualAcc);
    }

    @Test
    void Given_RequestAllUsers_When_Invoke_getAllUsers_Then_ReturnAllUsers() {
        UserDto userDto = new UserDto(8,"Beatriz","Pinzon","2025-03-24");
        UserDto userDto2 = new UserDto(9,"Armando","Mendoza","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        restTemplate.postForEntity(PATH_CREATE_USER,userDto2,UserEntity.class);
        List<UserEntity> expectedUsers = new ArrayList<>();
        List<UserEntity> pruebaUsers = new ArrayList<>();
        expectedUsers.add(new UserEntity(userDto.getDocument(),userDto.getName(),userDto.getLastName(),userDto.getDateCreated()));
        expectedUsers.add(new UserEntity(userDto2.getDocument(),userDto2.getName(),userDto2.getLastName(),userDto2.getDateCreated()));
        ResponseEntity<List<UserEntity>> response = restTemplate.exchange(PATH_ALL_USERS, HttpMethod.GET, null, new ParameterizedTypeReference<List<UserEntity>>() {});
        List<UserEntity> actualUsers = response.getBody();
        pruebaUsers.add(actualUsers.get(actualUsers.size()-2));
        pruebaUsers.add(actualUsers.get(actualUsers.size()-1));
        assertEquals(expectedUsers,pruebaUsers);
    }
}
