package br.com.AuthenticationMicrosservice.service.impl;

import br.com.AuthenticationMicrosservice.domain.dto.AuthenticationLoginDto;
import br.com.AuthenticationMicrosservice.domain.dto.TokenResponseDto;
import br.com.AuthenticationMicrosservice.domain.model.User;
import br.com.AuthenticationMicrosservice.repository.UserRepository;
import br.com.AuthenticationMicrosservice.service.AuthenticationService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${auth.jwt.token.secret-key}")
    private String secretKey;

    @Value("${auth.jwt.token.expiration}")
    private Integer horaExpiracaoToken;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer horaExpiracaoRefreshToken;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public TokenResponseDto obtainToken(AuthenticationLoginDto authDto) {
        // Realiza a autenticação do usuário
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authDto.email(), authDto.password());
        authenticationManager.authenticate(authenticationToken);

        // Busca o usuário autenticado no banco de dados
        User user = (User) loadUserByUsername(authDto.email());

        // Gera tokens de acesso e refresh
        String accessToken = buildToken(user, horaExpiracaoToken);
        String refreshToken = buildToken(user, horaExpiracaoRefreshToken);

        return TokenResponseDto.builder()
                .token(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    // Método responsável por validar o JWT e retornar o email do usuário
    @Override
    public String validateJWT(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Invalid or expired token");
        }
    }

    // Método responsável por obter um novo access token usando o refresh token
    @Override
    public TokenResponseDto obtainRefreshToken(String refreshToken) {
        // Valida o refresh token e obtém o e-mail do usuário
        String email = validateJWT(refreshToken);

        // Busca o usuário pelo e-mail
        User user = (User) loadUserByUsername(email);

        // Gera um novo access token e um novo refresh token
        String newAccessToken = buildToken(user, horaExpiracaoToken);
        String newRefreshToken = buildToken(user, horaExpiracaoRefreshToken);

        return TokenResponseDto.builder()
                .token(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }

    // Método responsável por criar um token JWT
    private String buildToken(User user, Integer expirationHours) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getEmail())
                    .withExpiresAt(generateExpirationDate(expirationHours))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Error while creating the token: " + exception.getMessage());
        }
    }

    // Método que carrega o usuário a partir do e-mail
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }
        return user;
    }

    // Método para gerar a data de expiração do token
    private Instant generateExpirationDate(Integer expirationHours) {
        return LocalDateTime.now()
                .plusHours(expirationHours)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}


