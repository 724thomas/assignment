package com.example.demo.api.auth.service;


import com.example.demo.api.auth.controller.request.LoginRequestBody;
import com.example.demo.api.auth.controller.request.SignupRequestBody;
import com.example.demo.api.auth.controller.response.AuthResponse;
import com.example.demo.domain.report.event.ReportEvent;
import com.example.demo.domain.role.Role;
import com.example.demo.domain.role.api.RoleApiRepository;
import com.example.demo.domain.user.User;
import com.example.demo.domain.user.api.UserApiRepository;
import com.example.demo.exception.BadRequestException;
import com.example.demo.util.JwtUtil;
import com.example.demo.util.SaltedHashUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserApiRepository userApiRepository;
    private final RoleApiRepository roleApiRepository;
    private final JwtUtil jwtUtil;
    private final SaltedHashUtil saltedHashUtil;
    private final ApplicationEventPublisher applicationEventPublisher;



    @Transactional
    public AuthResponse signup(SignupRequestBody body) {
        if (userApiRepository.existsByEmail(body.getEmail())) {
            throw new BadRequestException("auth.email.DUPLICATE");
        }

        String salt = saltedHashUtil.generateSalt();
        String hashedPassword = saltedHashUtil.hashPassword(body.getPassword(), salt);
        Role role = roleApiRepository.findByName(body.getRole()).orElseThrow(() -> new BadRequestException("Role not found"));

        User user = User.of(body.getEmail(),  body.getName(), salt, hashedPassword, role);
        userApiRepository.save(user);
        applicationEventPublisher.publishEvent(ReportEvent.of("SignUp", user.getId()));
        return createLoginRes(user);
    }

    @Transactional
    public AuthResponse login(LoginRequestBody body) {
        Optional<User> userOptional = userApiRepository.findByEmail(body.getEmail());

        // if user not found
        if (userOptional.isEmpty()) {
            throw new BadRequestException("Email not found");
        }

        // if password is incorrect
        if (!saltedHashUtil.verifyPassword(body.getPassword(), userOptional.get().getSalt(), userOptional.get().getHashedPassword())) {
            throw new BadRequestException("Password incorrect");
        }
        applicationEventPublisher.publishEvent(ReportEvent.of("Login", userOptional.get().getId()));
        return createLoginRes(userOptional.get());
    }

    private AuthResponse createLoginRes(User user) {
        String accessToken = jwtUtil.createAccessToken(user.getId(), user.getEmail());
        String refreshToken = jwtUtil.createRefreshToken(user.getId(), user.getEmail());

        userApiRepository.save(user);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
