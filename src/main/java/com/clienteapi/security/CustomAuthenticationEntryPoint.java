package com.clienteapi.security;

import io.cucumber.messages.internal.com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException authException) throws IOException {
        var authenticationError = new AuthenticationError();
        authenticationError.setStatus(HttpStatus.UNAUTHORIZED.value());
        authenticationError.setMessage(authException.getMessage());
        authenticationError.setException(authException.getClass().getSimpleName());
        authenticationError.setPath(req.getServletPath());
        var gson = new Gson();
        String jsonResponse = gson.toJson(authenticationError);
        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.getWriter().write(jsonResponse);
    }
}
