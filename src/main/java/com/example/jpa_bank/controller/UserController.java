package com.example.jpa_bank.controller;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class UserController {
    private UserService userService;
    @PostMapping(path = "/user/savings-user")
    public String createUser(@RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @GetMapping(path = "/account/check-accounts/{idDocument}")
    public String getAllAccounts(@PathVariable int idDocument) {
        return userService.consultAccounts(idDocument);
    }
}
