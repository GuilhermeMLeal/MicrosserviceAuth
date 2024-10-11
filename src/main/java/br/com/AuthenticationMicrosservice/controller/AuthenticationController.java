package br.com.AuthenticationMicrosservice.controller;

import br.com.AuthenticationMicrosservice.domain.dto.AuthenticationLoginDto;
import br.com.AuthenticationMicrosservice.domain.dto.RequestRefreshDto;
import br.com.AuthenticationMicrosservice.domain.dto.TokenResponseDto;
import br.com.AuthenticationMicrosservice.service.AuthenticationService;
import br.com.AuthenticationMicrosservice.service.impl.AuthenticationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto auth(@RequestBody AuthenticationLoginDto authDto) {
        return authenticationService.obtainToken(authDto);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.OK)
    public TokenResponseDto authRefreshToken(@RequestBody RequestRefreshDto refreshToken) {
        return authenticationService.obtainRefreshToken(refreshToken.refreshToken());
    }
}
