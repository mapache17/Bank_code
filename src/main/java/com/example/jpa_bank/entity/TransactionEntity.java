package com.example.jpa_bank.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "TRANSACTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity {
    @Id
    @Column(name ="id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name ="origen", nullable = false)
    private int origen;
    @Column(name ="destination", nullable = false)
    private int destination;
    @Column(name ="amount", nullable = false)
    private int amount;
}