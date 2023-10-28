package com.project.carrental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/cars")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/cars/**")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/cars/**")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.POST, "/api/categories")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/categories/**")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/categories/**")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.GET, "/api/reservations/**")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.PUT, "/api/reservations/**")).hasAnyRole("ADMIN")
                        .requestMatchers(antMatcher(HttpMethod.DELETE, "/api/reservations/**")).hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}
