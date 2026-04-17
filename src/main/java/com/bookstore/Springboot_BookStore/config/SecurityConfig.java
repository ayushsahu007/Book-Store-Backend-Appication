package com.bookstore.Springboot_BookStore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrfConfig -> csrfConfig.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**","/auth/**").permitAll()
                        .requestMatchers("/book/**").hasRole("ADMIN")
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                )
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
