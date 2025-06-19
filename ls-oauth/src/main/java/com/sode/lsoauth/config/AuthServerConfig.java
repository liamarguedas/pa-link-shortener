package com.sode.lsoauth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sode.lsoauth.entity.Jwks;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;

import java.util.UUID;

@Configuration
public class AuthServerConfig {
	
	@Value("${property.issuer}")
	private String issuer;

	@Bean
	SecurityFilterChain authServerSecurity(HttpSecurity http) throws Exception {
		http.with(OAuth2AuthorizationServerConfigurer.authorizationServer(), Customizer.withDefaults());
		return http.build();
	}

	@Bean
	RegisteredClientRepository registeredClientRepository() {

		RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("CLIENT_CHANGE_IN_PROD").clientSecret("SECRET_CHANGE_IN_PROD").scope("read").scope("write")
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS).build();

		return new InMemoryRegisteredClientRepository(registeredClient);
	}

	@Bean
	JWKSource<SecurityContext> jwkSource() {

		RSAKey rsaKey = Jwks.generateRsa();
		return (selector, context) -> selector.select(new JWKSet(rsaKey));
	}
	
	@Bean
	AuthorizationServerSettings authSettings() {
		return AuthorizationServerSettings.builder().issuer(issuer).build();
	}
}
