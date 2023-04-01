package com.example.jpa_bank.controller;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
public class UserController {
    private UserService userService;
    @PostMapping(path = "/user/savings-user")
    public UserEntity createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @GetMapping(path = "/account/check-accounts/{idDocument}")
    public List<AccountEntity> getAllAccounts(@PathVariable int idDocument) {
        return userService.consultAccounts(idDocument);
    }
}
