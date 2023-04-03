package com.example.jpa_bank.service;

import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    public UserEntity createUser(UserDto userDto) {
        return userRepository.save(new UserEntity(userDto.getDocument(),userDto.getName(),userDto.getLastName(),userDto.getDateCreated()));
    }
    public List<AccountEntity> consultAccounts(int documentUser) {
        if(!this.userRepository.existsById(documentUser)){
            throw new RuntimeException("El usuario que quiere consultar no existe.");
        }
        return accountRepository.getAllAccounts(documentUser);
    }

}
