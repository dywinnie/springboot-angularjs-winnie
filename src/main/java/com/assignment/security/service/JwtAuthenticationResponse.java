package com.assignment.security.service;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1250166508152483573L;

    private final String userName;

    private final String token;

    public JwtAuthenticationResponse(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public String getUserName() {
        return this.userName;
    }
}
