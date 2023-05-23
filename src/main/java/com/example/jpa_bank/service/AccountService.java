package com.example.jpa_bank.service;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.DepositMoneyUserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountRepository accountRepository;
    private UserRepository userRepository;
    public AccountEntity insertAccount(AccountDto accountDto){
        int size=accountRepository.getAllAccounts(accountDto.getUser()).size();
        if(!(size<4)) {
            throw new RuntimeException("Excede el numero de cuentas registradas.");
        }
        if(!this.userRepository.existsById(accountDto.getUser())){
            throw new RuntimeException("El usuario no existe.");
        }
        return accountRepository.save(new AccountEntity(accountDto.getId(),accountDto.getType(),accountDto.getMoney(),accountDto.getDateCreated(),accountDto.getUser()));
    }

    public AccountEntity checkBalance(int accountNumber){
        if(!this.accountRepository.existsById(accountNumber)){
            throw new RuntimeException("La cuenta a la que quiere consultar no existe.");
        }
        return accountRepository.findById(accountNumber).orElse(new AccountEntity());
    }
}