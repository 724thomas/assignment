package com.example.demo.api.auth.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequestBody {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
