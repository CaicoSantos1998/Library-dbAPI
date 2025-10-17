package io.github.CaicoSantos1998.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record ClientDTO(
        UUID id,
        @NotBlank(message = "Required field!")
        String clientId,
        @NotBlank(message = "Required field!")
        String clientSecret,
        @NotBlank(message = "Required field!")
        String redirectURI,
        String scope
) {
}
