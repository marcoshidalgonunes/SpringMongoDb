package com.springmongodb.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenAPISecurityConfig {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    String authServerUrl;

    private static final String OAUTH_SCHEME_NAME = "security_schema";

    @Bean
    OpenAPI openAPI() {
        return new OpenAPI().components(new Components()
            .addSecuritySchemes(OAUTH_SCHEME_NAME, createOAuthScheme()))
            .addSecurityItem(new SecurityRequirement().addList(OAUTH_SCHEME_NAME))
            .info(new Info().title("Book Catalog Service")
                .description("A service providing book catalog.")
                .version("1.0"));
    }

    private SecurityScheme createOAuthScheme() {
        OAuthFlows flows = createOAuthFlows();
        return new SecurityScheme().type(SecurityScheme.Type.OAUTH2)
            .flows(flows);
    }

    private OAuthFlows createOAuthFlows() {
        OAuthFlow flow = createAuthorizationCodeFlow();
        return new OAuthFlows().implicit(flow);
    }

    private OAuthFlow createAuthorizationCodeFlow() {
        return new OAuthFlow()
            .authorizationUrl(authServerUrl + "/protocol/openid-connect/auth")
            .scopes(new Scopes().addString("openid", "openid"));
    }
}