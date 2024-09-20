package br.com.AuthenticationMicrosservice.domain.dto;

import br.com.AuthenticationMicrosservice.domain.enums.UserRoles;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record RegisterUserDto(
        @NotEmpty String firstName,
        @NotEmpty String lastName,
        @NotEmpty String email,
        @NotEmpty String password,
        @NotNull Boolean isActive,
        @NotNull UserRoles role
) {
}
