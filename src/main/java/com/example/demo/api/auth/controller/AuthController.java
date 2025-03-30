package com.example.demo.api.auth.controller;


import com.example.demo.api.auth.controller.request.LoginRequestBody;
import com.example.demo.api.auth.controller.request.SignupRequestBody;
import com.example.demo.api.auth.controller.response.AuthResponse;
import com.example.demo.api.auth.service.AuthService;
import com.example.demo.response.DataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public DataResponse<AuthResponse> emailSignup(@Valid @RequestBody SignupRequestBody body) {
        return DataResponse.of(authService.signup(body));
    }

    @PostMapping("/login")
    public DataResponse<AuthResponse> emailLogin(@Valid @RequestBody LoginRequestBody body) {
        return DataResponse.of(authService.login(body));
    }
}