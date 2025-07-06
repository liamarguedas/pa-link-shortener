package com.sode.lsoauth.resource;

import com.sode.lsoauth.dto.LoginRequest;
import com.sode.lsoauth.dto.TokenResponse;
import com.sode.lsoauth.entity.User;
import com.sode.lsoauth.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;




@RestController
@RequestMapping("/api/auth")
public class AuthResource {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    @Autowired
    private UserRepository userRepository;

    private final Logger log = LoggerFactory.getLogger(AuthResource.class);

    public AuthResource(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    @GetMapping("/test/{username}")
    public ResponseEntity<User> testing(@PathVariable("username") String username) {

        User u = userRepository.findByUsername(username).get();

        return ResponseEntity.ok().body(u);
    }

    @PostMapping("/user")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        log.info("Iniciando login: {}", request.toString());

        try {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            Instant now = Instant.now();

            JwtClaimsSet claims = JwtClaimsSet.builder()

                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plusSeconds(3600*24))
                    .subject(request.username())
                    .build();

            String token = jwtEncoder.encode(
                    JwtEncoderParameters.from(claims)
            ).getTokenValue();

            return ResponseEntity.ok(new TokenResponse(token));


        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

}
