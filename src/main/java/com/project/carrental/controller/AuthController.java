package com.project.carrental.controller;

import com.project.carrental.dto.request.LoginRequest;
import com.project.carrental.dto.response.AuthResponse;
import com.project.carrental.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        return new ResponseEntity<>(authService.validateUser(loginRequest), HttpStatus.OK);
    }
}
