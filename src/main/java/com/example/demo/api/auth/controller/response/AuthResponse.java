package com.example.demo.api.auth.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
public class AuthResponse implements Serializable {

    private String accessToken;

    private String refreshToken;
}