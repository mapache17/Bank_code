package com.example.jpa_bank.integration.controller;
import com.example.jpa_bank.AbstractTest;
import com.example.jpa_bank.controller.dto.AccountDto;
import com.example.jpa_bank.controller.dto.DepositMoneyUserDto;
import com.example.jpa_bank.controller.dto.TransactionDto;
import com.example.jpa_bank.controller.dto.UserDto;
import com.example.jpa_bank.entity.AccountEntity;
import com.example.jpa_bank.entity.TransactionEntity;
import com.example.jpa_bank.entity.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

class TransactionControllerTest extends AbstractTest {
    private static final String PATH_CREATE_ACCOUNT = "/account/savings-account";
    private static final String PATH_CREATE_USER = "/user/savings-user";
    private static final String PATH_DO_TRANSACTION = "/transaction/transfer-money";
    private static final String PATH_DO_DEPOSIT_MONEY = "/transaction/deposit-money";
    private static final String PATH_CHECK_BALANCE = "/account/check-balance/";


    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void Given_NonExistingOriginAccount_When_Invoke_doTransaction_Then_InternalServerError() {
        TransactionDto transactionDto=new TransactionDto(1,5,1,100);
        ResponseEntity<TransactionEntity> transactionEntityResponseEntity= restTemplate.postForEntity(PATH_DO_TRANSACTION, transactionDto, TransactionEntity.class);
        assertEquals(HttpStatusCode.valueOf(500), transactionEntityResponseEntity.getStatusCode());
    }
    @Test
    void Given_NonExistingDestinationAccount_When_Invoke_doTransaction_Then_InternalServerError() {
        TransactionDto transactionDto=new TransactionDto(1,1,6,100);
        ResponseEntity<TransactionEntity> transactionEntityResponseEntity= restTemplate.postForEntity(PATH_DO_TRANSACTION, transactionDto, TransactionEntity.class);
        assertEquals(HttpStatusCode.valueOf(500), transactionEntityResponseEntity.getStatusCode());
    }
    @Test
    void Given_InsufficientFunds_When_Invoke_doTransaction_Then_InternalServerError(){
        UserDto userOrigin = new UserDto(1,"Sebas","Acosta","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userOrigin, UserEntity.class);
        AccountDto accountOrigin = new AccountDto(1,"Ahorro",10,"2025-03-24",userOrigin.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountOrigin, AccountEntity.class);
        UserDto userDestination = new UserDto(2,"Juan","Otero","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDestination, UserEntity.class);
        AccountDto accountDestination = new AccountDto(2,"Ahorro",10,"2025-03-24",userDestination.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDestination, AccountEntity.class);

        TransactionDto transactionDto=new TransactionDto(1,1,2,100);
        ResponseEntity<TransactionEntity> transactionEntityResponseEntity= restTemplate.postForEntity(PATH_DO_TRANSACTION, transactionDto, TransactionEntity.class);
        assertEquals(HttpStatusCode.valueOf(500), transactionEntityResponseEntity.getStatusCode());
    }
    @Test
    void Given_ExistingOriginAccountWithExistingDestinationAccountWithEnoughMoney_When_Invoke_doTransaction_Then_Return_doANewTransaction(){
        UserDto userOrigin = new UserDto(1,"Mike","Wazowski","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userOrigin, UserEntity.class);
        AccountDto accountOrigin = new AccountDto(1,"Ahorro",1000,"2025-03-24",userOrigin.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountOrigin, AccountEntity.class);
        UserDto userDestination = new UserDto(2,"Firu","Lais","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDestination, UserEntity.class);
        AccountDto accountDestination = new AccountDto(2,"Ahorro",10,"2025-03-24",userDestination.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDestination, AccountEntity.class);

        TransactionDto transactionDto=new TransactionDto(1,1,2,500);
        ResponseEntity<TransactionEntity> transactionEntityResponseEntity= restTemplate.postForEntity(PATH_DO_TRANSACTION, transactionDto, TransactionEntity.class);
        assertEquals(new TransactionEntity(1,1,2,500), transactionEntityResponseEntity.getBody());
    }
    @Test
    void Give_AnExistingAccount_When_Invoke_depositMoney_Then_TheAccountIsRecharged()
    {
        UserDto userDto = new UserDto(1,"Jhoan","Ome","2025-03-24");
        restTemplate.postForEntity(PATH_CREATE_USER,userDto,UserEntity.class);
        AccountDto accountDto = new AccountDto(1,"Ahorro",20,"2025-03-24",userDto.getDocument());
        restTemplate.postForEntity(PATH_CREATE_ACCOUNT, accountDto, AccountEntity.class);
        DepositMoneyUserDto depositMoneyUserDto = new DepositMoneyUserDto(10,accountDto.getId());
        restTemplate.put(PATH_DO_DEPOSIT_MONEY,depositMoneyUserDto, AccountEntity.class);
        int money=depositMoneyUserDto.getMoneyAmount()+accountDto.getMoney();
        ResponseEntity<AccountEntity> accountEntityResponseEntity= restTemplate.getForEntity(PATH_CHECK_BALANCE+accountDto.getId(), AccountEntity.class);
        assertEquals(money, accountEntityResponseEntity.getBody().getMoney());

    }
}