package com.example.userservice.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class RequestUser {
    @NotNull(message = "Email cannot be null")
    @Email
    private String email;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be 2~20 characters")
    private String name;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 15, message = "Password must be 8~15 characters")
    private String pwd;
}
