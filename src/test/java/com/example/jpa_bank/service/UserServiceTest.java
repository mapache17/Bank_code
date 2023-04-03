package com.example.jpa_bank.service;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.UserEntity;
import com.example.jpa_bank.repository.AccountRepository;
import com.example.jpa_bank.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @InjectMocks
    UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private AccountRepository accountRepository;
    @Test
    void Given_UserDataInDto_When_Invoke_createUser_Then_Return_UserEntity() {
        UserDto userDto = new UserDto(5,"Pepito","Perez","2025-03-24");
        Mockito.when(userService.createUser(userDto)).thenReturn(new UserEntity(5,"Pepito","Perez","2025-03-24"));
        UserEntity user = userService.createUser(userDto);
        Assertions.assertEquals(new UserEntity(5,"Pepito","Perez","2025-03-24"), user);
        Mockito.verify(userRepository).save(new UserEntity(5,"Pepito","Perez","2025-03-24"));
    }
    @Test
    void Given_UserDocumentExists_When_Invoke_consultAccounts_Then_Return_AccountEntityList() {
        UserDto userDto = new UserDto(10,"Lola","Londoño","2025-03-24");
        ArrayList<AccountEntity> expectedAccounts = new ArrayList<>();
        expectedAccounts.add(new AccountEntity(  1,"Ahorro",100,"2025-02-24",10));
        expectedAccounts.add(new AccountEntity(  2,"Corriente",200,"2025-03-24",10));
        expectedAccounts.add(new AccountEntity(  3,"Ahorro",300,"2025-04-24",10));
        Mockito.when(userRepository.existsById(userDto.getDocument())).thenReturn(true);
        Mockito.when(accountRepository.getAllAccounts(userDto.getDocument())).thenReturn(expectedAccounts);
        List<AccountEntity> accounts = userService.consultAccounts(userDto.getDocument());
        Assertions.assertEquals(expectedAccounts, accounts);
        Mockito.verify(userRepository).existsById(userDto.getDocument());
        Mockito.verify(accountRepository).getAllAccounts(userDto.getDocument());
    }
    @Test
    void Given_UserDocumentDONTExists_When_Invoke_consultAccounts_Then_throwRuntimeException() {
        UserDto userDto = new UserDto(2,"Tomate","Torrez","2025-03-24");
        Mockito.when(userRepository.existsById(userDto.getDocument())).thenReturn(false);
        Assertions.assertThrows(RuntimeException.class, () ->{
            userService.consultAccounts(userDto.getDocument());
        });
        Mockito.verify(userRepository).existsById(userDto.getDocument());

    }
}