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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Value("${auth.jwt.token.secret-key}")
    private  String secretKey;

    @Value("${auth.jwt.token.expiration}")
    private Integer horaExpiracaoToken = 1;

    @Value("${auth.jwt.refresh-token.expiration}")
    private Integer horaExpiracaoRefreshToken = 8;

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public TokenResponseDto obterToken(AuthenticationLoginDto authDto) {
        User user = userRepository.findByEmail(authDto.email());
        return TokenResponseDto
                .builder()
                .token(buildToken(user, horaExpiracaoToken))
                .refreshToken(buildToken(user, horaExpiracaoRefreshToken))
                .build();
    }

    public  String buildToken(User usuario, Integer expiration) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(usuario.getEmail())
                    .withExpiresAt(geraDataExpiracao(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro ao tentar gerar o token! " +exception.getMessage());
        }
    }

    @Override
    public TokenResponseDto obterRefreshToken(String refreshToken) {

        String login = validaTokenJwt(refreshToken);
        User usuario = userRepository.findByEmail(login);

        if (usuario == null) {
            throw  new RuntimeException("Unauthorizad");
        }

        var autentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(autentication);

        return TokenResponseDto
                .builder()
                .token(buildToken(usuario, horaExpiracaoToken))
                .refreshToken(buildToken(usuario, horaExpiracaoRefreshToken))
                .build();
    }

    public String validaTokenJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Instant geraDataExpiracao(Integer expiration) {
        return LocalDateTime.now()
                .plusHours(expiration)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}

