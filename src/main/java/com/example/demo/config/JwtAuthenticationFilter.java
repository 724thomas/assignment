package com.example.demo.config;


import com.example.demo.domain.user.User;
import com.example.demo.domain.user.api.UserApiRepository;
import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserApiRepository userApiRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = extractAccessToken(request);
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Claims claims = jwtUtil.extractAccessClaims(token);

            Long userId = ((Integer) claims.get("userId")).longValue();

            // 역할 가져오기 (캐싱 가능)
            User user = userApiRepository.findById(userId)
                    .orElseThrow(() -> new BadCredentialsException("User not found"));
            String role = user.getRole().getName();

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userId,
                            null,
                            List.of(new SimpleGrantedAuthority(role))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
            throw new BadCredentialsException("JWT token has expired");
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            log.error("Invalid JWT token", e);
            throw new BadCredentialsException("Invalid JWT token");
        }

        filterChain.doFilter(request, response);
    }

    // 토큰 추출
    // accessToken 또는 null 반환
    private String extractAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        return (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) ?
                bearerToken.substring(7) : null;
    }

}
