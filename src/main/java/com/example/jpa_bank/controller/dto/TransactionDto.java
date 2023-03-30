package com.example.jpa_bank.controller.dto;

import lombok.Data;

import java.math.BigInteger;

@Data
public class TransactionDto {
    private int id;
    private int origen;
    private int destination;
    private int amount;

}
