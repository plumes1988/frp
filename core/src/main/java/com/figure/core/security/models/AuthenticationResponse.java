package com.figure.core.security.models;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

    private final String status;

    private final String jwt;

    public AuthenticationResponse(String jwt,String status) {
        this.jwt = jwt;
        this.status = status;
    }

    public String getJwt() {
        return jwt;
    }

    public String getStatus() {
        return status;
    }
}
