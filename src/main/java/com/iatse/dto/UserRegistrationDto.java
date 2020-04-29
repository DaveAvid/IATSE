package com.iatse.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
public class UserRegistrationDto {

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    @Size(min=8, max=32)
    private String password;

    @NotEmpty
    @Size(min=8, max=32)
    private String confirmPassword;

    @NotEmpty
    private String accessCode;

    @Email
    @NotEmpty
    private String email;

    @Email
    @NotEmpty
    private String confirmEmail;
}