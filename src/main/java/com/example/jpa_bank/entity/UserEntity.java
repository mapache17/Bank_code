package com.example.jpa_bank.entity;

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
    @Column(name ="document", nullable = false)
    private Integer document;
    @Column(name ="name", nullable = false)
    private String name;
    @Column(name ="last_name", nullable = false)
    private String lastName;
    @Column(name ="date_created", nullable = false)
    private String dateCreated;
}