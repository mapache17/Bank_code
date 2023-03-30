package com.example.jpa_bank.controller.dto;

import lombok.Data;

@Data
public class UserDto {
    private int document;
    private String name;
    private String last_name;
    private String date_created;
}
