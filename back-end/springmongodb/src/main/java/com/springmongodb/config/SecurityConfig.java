package com.springmongodb.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests()
            .requestMatchers("/api/Books/**").authenticated()
            .requestMatchers("/oauth2-redirect.html", "/swagger-ui.html", "/v3/api-docs", "/swagger-ui/index.html", "/swagger-ui/**",
                    "/api/loggedin/confirm/**", "/api/loggedin/confirm/", "/public/oauth2-redirect.html", "/context-path/v3/api-docs", "/context-path/v3/api-docs/**", "/swagger-ui-custom.html").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .oauth2ResourceServer(server -> server.jwt());
            
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
    }    
}
