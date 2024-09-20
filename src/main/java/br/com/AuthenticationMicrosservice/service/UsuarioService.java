package br.com.AuthenticationMicrosservice.service;

import br.com.AuthenticationMicrosservice.domain.dto.RegisterUserDto;

public interface UsuarioService {
    public RegisterUserDto salvar(RegisterUserDto usuarioDto);
}
