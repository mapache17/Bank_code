package com.example.jpa_bank.integration.controller;
import com.example.jpa_bank.AbstractTest;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.DepositMoneyUserDto;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.service.UserService;
import org.apache.catalina.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;


class UserControllerTest extends AbstractTest {
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

    }
    @Test
    void Given_RequestUserAccountsNONExistingUser_When_Invoke_getAllAccounts_Then_ConsultAccounts() {
    }

    @Test
    void Given_RequestAllUsers_When_Invoke_getAllUsers_Then_ReturnAllUsers() {
        UserDto userDto = new UserDto(8,"Beatriz","Pinzon","2025-03-24");
        UserDto userDto2 = new UserDto(9,"Armando","Mendoza","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        restTemplate.postForEntity(PATH_CREATE_USER,userDto2,UserEntity.class);
        ArrayList<UserEntity> expectedUsers = new ArrayList<>();
        expectedUsers.add(new UserEntity(userDto.getDocument(),userDto.getName(),userDto.getLastName(),userDto.getDateCreated()));
        expectedUsers.add(new UserEntity(userDto2.getDocument(),userDto2.getName(),userDto2.getLastName(),userDto2.getDateCreated()));
        ResponseEntity<ArrayList<UserEntity>> userEntityResponseEntity = restTemplate.exchange(PATH_ALL_USERS, HttpMethod.GET, null, new ParameterizedTypeReference<ArrayList<UserEntity>>() {});
        assertEquals(expectedUsers,userEntityResponseEntity.getBody());
    }
}
