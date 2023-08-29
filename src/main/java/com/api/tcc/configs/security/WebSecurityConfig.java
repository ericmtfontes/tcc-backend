package com.api.tcc.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig{

    @Autowired
    FilterToken filter;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        return http.cors().and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/users/login")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/users/register")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/cars")
                .permitAll()
                .requestMatchers(HttpMethod.GET, "/cars/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/cars").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/cars/rent").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.PUT, "/cars/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/cars/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
