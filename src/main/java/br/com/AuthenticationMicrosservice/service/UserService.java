package br.com.AuthenticationMicrosservice.service;

import br.com.AuthenticationMicrosservice.domain.dto.RegisterUserDto;

public interface UserService {
    public RegisterUserDto salvar(RegisterUserDto usuarioDto);
}
