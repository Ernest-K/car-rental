package com.project.carrental.service;

import com.project.carrental.dto.request.LoginRequest;
import com.project.carrental.dto.response.AuthResponse;
import com.project.carrental.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    UserDetailsService userDetailsService;

    @InjectMocks
    AuthServiceImpl authService;

    @Test
    void validateUserShouldReturnAuthResponse(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test");
        loginRequest.setPassword("test");
        UserDetails userDetails = createSampleUserDetails();
        when(userDetailsService.loadUserByUsername(loginRequest.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())).thenReturn(true);

        AuthResponse authResponse = authService.validateUser(loginRequest);

        assertThat(authResponse).isNotNull();
    }

    @Test
    void validateUserShouldThrowAuthenticationCredentialsNotFoundException(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("test");
        loginRequest.setPassword("test");
        UserDetails userDetails = createSampleUserDetails();
        when(userDetailsService.loadUserByUsername(loginRequest.getUsername())).thenReturn(userDetails);
        when(passwordEncoder.matches(loginRequest.getPassword(), userDetails.getPassword())).thenReturn(false);

        assertThatThrownBy(() -> {
            AuthResponse authResponse = authService.validateUser(loginRequest);
        }).isInstanceOf(AuthenticationCredentialsNotFoundException.class);
    }

    UserDetails createSampleUserDetails(){
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of(new GrantedAuthority() {
                    @Override
                    public String getAuthority() {
                        return "ADMIN";
                    }
                });
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return null;
            }

            @Override
            public boolean isAccountNonExpired() {
                return false;
            }

            @Override
            public boolean isAccountNonLocked() {
                return false;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return false;
            }

            @Override
            public boolean isEnabled() {
                return false;
            }
        };
    }

}