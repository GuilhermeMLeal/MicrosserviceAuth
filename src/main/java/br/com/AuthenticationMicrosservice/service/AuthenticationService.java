package br.com.AuthenticationMicrosservice.service;

import br.com.AuthenticationMicrosservice.domain.dto.AuthenticationLoginDto;
import br.com.AuthenticationMicrosservice.domain.dto.TokenResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthenticationService extends UserDetailsService {
    public TokenResponseDto obterToken(AuthenticationLoginDto authDto);
    public String validaTokenJwt(String token);
    public TokenResponseDto obterRefreshToken(String refreshToken);
}
