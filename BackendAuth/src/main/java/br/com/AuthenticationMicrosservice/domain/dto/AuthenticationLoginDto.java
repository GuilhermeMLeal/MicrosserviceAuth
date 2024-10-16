package br.com.AuthenticationMicrosservice.domain.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationLoginDto(@NotNull String email, @NotNull String password){
}
