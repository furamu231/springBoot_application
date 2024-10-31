package com.spring.springbootapplication.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/users/signup", "/users/signin", "/users/success", "/css/**").permitAll() 
                .anyRequest().authenticated()
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/users/signin") 
                .loginProcessingUrl("/users/signin")  
                .defaultSuccessUrl("/users/success", true)
                .failureUrl("/users/signin?error=true")  
                .permitAll()
            );
            // .logout(logout -> logout
            //     .logoutUrl("/logout")
            //     .logoutSuccessUrl("/login")
            //     .permitAll()
            // );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}