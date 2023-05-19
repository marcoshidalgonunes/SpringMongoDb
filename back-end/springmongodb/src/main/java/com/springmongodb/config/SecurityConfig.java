package com.springmongodb.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers("/api/Books/**").authenticated()
                .requestMatchers("/oauth2-redirect.html", "/swagger-ui.html", "/v3/api-docs", "/swagger-ui/index.html", "/swagger-ui/**",
                        "/api/loggedin/confirm/**", "/api/loggedin/confirm/", "/public/oauth2-redirect.html", "/context-path/v3/api-docs", "/context-path/v3/api-docs/**", "/swagger-ui-custom.html").permitAll()
                .anyRequest()
                .authenticated()     
            )
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt
                .decoder(JwtDecoders.fromIssuerLocation(issuerUri))
            )
        );

        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/v3/api-docs/**");
    }    
}
