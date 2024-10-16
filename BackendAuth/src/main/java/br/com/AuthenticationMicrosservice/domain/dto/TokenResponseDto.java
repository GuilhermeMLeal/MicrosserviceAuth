package br.com.AuthenticationMicrosservice.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record TokenResponseDto(@NotEmpty String token, @NotEmpty String refreshToken) {
}
