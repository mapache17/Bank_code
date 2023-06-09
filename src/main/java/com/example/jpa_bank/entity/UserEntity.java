package com.example.jpa_bank.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_IDENTITY")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
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