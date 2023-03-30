package com.example.jpa_bank.controller.dto;

import lombok.Data;

@Data
public class DepositMoneyUserDto {
    private int moneyAmount;
    private int accountNumber;
}
