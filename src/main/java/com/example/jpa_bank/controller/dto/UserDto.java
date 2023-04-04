package com.example.jpa_bank.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDto {
    private int document;
    private String name;
    private String lastName;
    private String dateCreated;
}
