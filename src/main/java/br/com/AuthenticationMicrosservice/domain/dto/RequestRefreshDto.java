package br.com.AuthenticationMicrosservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;

public record RequestRefreshDto(@NotEmpty String refreshToken) {}