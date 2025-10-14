package io.github.CaicoSantos1998.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsersDTO(
        @NotBlank(message = "Required field!")
        String login,

        @NotBlank(message = "Required field!")
        String password,

        @Email(message = "Is not a valid email!")
        @NotBlank(message = "Required field!")
        String email,

        List<String> roles) {
}
