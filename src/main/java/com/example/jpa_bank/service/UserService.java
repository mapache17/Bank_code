package com.example.jpa_bank.service;

import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    public String createUser(UserDto userDto) {
        userRepository.save(new UserEntity(userDto.getDocument(),userDto.getName(),userDto.getLastName(),userDto.getDateCreated()));
        return "An user was created";
    }
    public String consultAccounts(int documentUser) {
        try {
        UserEntity actualUser = userRepository.findById(documentUser).orElse(new UserEntity());
        return "The user: "+actualUser.getName()+" has: "+accountRepository.getAllAccounts(documentUser);
        }
        catch (Exception e){
        return "the user does not exist or does not have an account";
        }
    }
}
