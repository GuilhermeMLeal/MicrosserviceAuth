package br.com.AuthenticationMicrosservice.domain.dto;

import br.com.AuthenticationMicrosservice.domain.enums.UserRoles;

public record RegisterUserDto(String email, String password, UserRoles role) {
}
