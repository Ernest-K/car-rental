package com.project.carrental.service;

import com.project.carrental.dto.request.LoginRequest;
import com.project.carrental.dto.response.AuthResponse;

public interface AuthService {
    AuthResponse validateUser(LoginRequest loginRequest);
}
