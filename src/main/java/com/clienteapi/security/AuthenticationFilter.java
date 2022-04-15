package com.clienteapi.security;

import com.clienteapi.security.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PATHS_TO_IGNORE_AUTHENTICATE = Arrays.asList("/actuator/health");

    private final UserRepository userRepository;

    @Autowired
    public AuthenticationFilter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!hasPathToIgnoreAuthenticate(request.getServletPath())) {
                if (!isAuthorized(request.getHeader("Authorization"))) {
                    throw new InsufficientAuthenticationException("User not authorized");
                }
                setToken(request);
            }
        } catch (Exception e) {
            String errorName = e.getClass() != null ? e.getClass().getSimpleName() : "";
            log.error("AuthenticationFilter - [" + errorName + "] :" + e.getMessage());
            SecurityContextHolder.getContext().setAuthentication(null);
        } finally {
            filterChain.doFilter(request, response);
        }
    }

    private boolean hasPathToIgnoreAuthenticate(String servletPath) {
        for (String pattern : PATHS_TO_IGNORE_AUTHENTICATE) {
            if (servletPath.contains(pattern)) {
                return true;
            }
        }
        return false;
    }

    private boolean isAuthorized(String authorization) {
        final var userPassword = getUserPasswordFromHeader(authorization);
        return userRepository.findByUserAndPassword(userPassword[0], userPassword[1]).isPresent();
    }

    private String[] getUserPasswordFromHeader(String authorization) {
        String base64Credentials = authorization.substring("Basic".length()).trim();
        byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
        var credentials = new String(credDecoded, StandardCharsets.UTF_8);
        return credentials.split(":", 2);
    }

    private void setToken(HttpServletRequest request) {
        final var userPassword = getUserPasswordFromHeader(request.getHeader("Authorization"));
        var token = new PreAuthenticatedAuthenticationToken(userPassword[0], new ArrayList<>());
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
