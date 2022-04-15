package com.clienteapi.security;

import lombok.Data;

@Data
public class AuthenticationError {

    private int status;
    private String error;
    private String exception;
    private String message;
    private String path;

}
