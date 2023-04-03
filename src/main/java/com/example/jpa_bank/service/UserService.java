package com.example.jpa_bank.service;

import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository userRepository;
    private AccountRepository accountRepository;
    public UserEntity createUser(UserDto userDto) {
        return userRepository.save(new UserEntity(userDto.getDocument(),userDto.getName(),userDto.getLastName(),userDto.getDateCreated()));
    }
    public List<AccountEntity> consultAccounts(int documentUser) {
        return accountRepository.getAllAccounts(documentUser);
    }

}
