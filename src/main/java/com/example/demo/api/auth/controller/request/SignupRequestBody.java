package com.example.demo.api.auth.controller.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SignupRequestBody {

    //email, password, role

    @NotEmpty()
    private String email;

    @NotEmpty()
    private String password;

    @NotEmpty()
    private String name;

    @NotEmpty()
    private String role;
}
