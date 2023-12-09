package com.project.carrental.service.impl;

import com.project.carrental.dto.request.LoginRequest;
import com.project.carrental.dto.response.AuthResponse;
import com.project.carrental.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    @Override
    public AuthResponse validateUser(LoginRequest loginRequest) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
        if(passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())){
            return new AuthResponse(userDetails.getUsername(), userDetails.getAuthorities().toString());
        }

        throw new AuthenticationCredentialsNotFoundException("Credentials not found");
    }
}
