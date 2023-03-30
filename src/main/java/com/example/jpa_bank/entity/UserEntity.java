package com.example.jpa_bank.entity;

import java.math.BigInteger;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserEntity {
    @Id
    private Integer document;
    private String name;
    private String last_name;
    private String date_created;

}