package com.sode.lsoauth.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.sode.lsoauth.entity.Jwks;
import com.sode.lsoauth.properties.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import java.time.Duration;
import java.util.UUID;

@Configuration
public class AuthServerConfig {

	private final JwtProperties jwtProperties;
	private final ClientProperties clientProperties;
	private final RevokerProperties revokerProperties;
	private final OAuthProperties OAuthProperties;
	private final UserProperties userProperties;
	private final CorsConfigurationSource corsConfigurationSource;

	public AuthServerConfig(
			JwtProperties jwtProperties,
			ClientProperties clientProperties,
			RevokerProperties revokerProperties,
			OAuthProperties OAuthProperties,
			UserProperties userProperties,
			CorsConfigurationSource corsConfigurationSource) {

		this.jwtProperties = jwtProperties;
		this.clientProperties = clientProperties;
		this.revokerProperties = revokerProperties;
		this.OAuthProperties = OAuthProperties;
		this.userProperties = userProperties;
		this.corsConfigurationSource = corsConfigurationSource;
	}

	@Bean
	JWKSource<SecurityContext> jwkSource() {
		RSAKey rsaKey = Jwks.generateRsa();
		return (selector, context) -> selector.select(new JWKSet(rsaKey));
	}

	@Bean
	AuthorizationServerSettings authSettings() {
		return AuthorizationServerSettings.builder().issuer(OAuthProperties.getIssuer()).build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authProvider);
	}

	@Bean
	SecurityFilterChain authServerSecurity(HttpSecurity http) throws Exception {

		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigurationSource))
				.httpBasic(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/auth/login").permitAll()
						.anyRequest().authenticated())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.formLogin(AbstractHttpConfigurer::disable)
				.with(new OAuth2AuthorizationServerConfigurer(), Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
		return new NimbusJwtEncoder(jwkSource);
	}

	@Bean
	JdbcOAuth2AuthorizationService authorizationService(
			JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository);
	}

	@Bean
	JdbcOAuth2AuthorizationConsentService authorizationConsentService(
			JdbcTemplate jdbcTemplate, RegisteredClientRepository registeredClientRepository) {
		return new JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository);
	}

	@Bean
	RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
		return new JdbcRegisteredClientRepository(jdbcTemplate);
	}

	@Bean
	CommandLineRunner initClients(RegisteredClientRepository registeredClientRepository) {

		JdbcRegisteredClientRepository jdbcRepository = (JdbcRegisteredClientRepository) registeredClientRepository;

		return args -> {

			if (jdbcRepository.findByClientId(userProperties.getClient()) == null) {

				// USER CLIENT LOGIN/REGISTER

				RegisteredClient userClient = RegisteredClient
						.withId(UUID.randomUUID().toString())
						.clientId(userProperties.getClient())
						.clientSecret(passwordEncoder().encode(userProperties.getSecret()))
						.redirectUri("https://oauth.pstmn.io/v1/callback") // POSTMAN PORT, CHANGE FOR PROD REDIRECT
						.redirectUri(OAuthProperties.getIssuer() + "/login/oauth2/code/test")
						.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
						.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
						.scope("read")
						.scope("write")
						.tokenSettings(TokenSettings.builder()
								.accessTokenTimeToLive(Duration.ofDays(1))
								.refreshTokenTimeToLive(Duration.ofDays(30))
								.reuseRefreshTokens(true)
								.build())
						.build();

				jdbcRepository.save(userClient);
			}

			if(jdbcRepository.findByClientId(revokerProperties.getClient()) == null) {

			// REVOKER MICROSERVICE AUTH

			RegisteredClient lsRevokerClient = RegisteredClient
					.withId(UUID.randomUUID().toString())
					.clientId(revokerProperties.getClient())
					.clientSecret(passwordEncoder().encode(revokerProperties.getSecret()))
					.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
					.scope("service:revoke")
					.build();

			jdbcRepository.save(lsRevokerClient);
		}
			if(jdbcRepository.findByClientId(clientProperties.getClient()) == null) {

				// USER MICROSERVICE AUTH

				RegisteredClient lsUserClient = RegisteredClient
						.withId(UUID.randomUUID().toString())
						.clientId(clientProperties.getClient())
						.clientSecret(passwordEncoder().encode(clientProperties.getSecret()))
						.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
						.scope("service:user")
						.build();
				jdbcRepository.save(lsUserClient);
			}
	};
}
}


