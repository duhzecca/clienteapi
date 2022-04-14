package com.clienteapi.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

    private static final List<String> PATHS_TO_IGNORE_AUTHENTICATE = Arrays.asList("/actuator/health");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!hasPathToIgnoreAuthenticate(request.getServletPath())) {
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

    private void setToken(HttpServletRequest request) {
        var token = new PreAuthenticatedAuthenticationToken("", new ArrayList<>());
        token.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
