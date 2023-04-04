package com.example.jpa_bank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DepositMoneyUserDto {
    private int moneyAmount;
    private int accountNumber;
}
