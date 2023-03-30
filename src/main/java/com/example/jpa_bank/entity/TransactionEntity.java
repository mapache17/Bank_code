package com.example.jpa_bank.entity;

import java.math.BigInteger;
import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int origen;
    private int destination;
    private int amount;


}