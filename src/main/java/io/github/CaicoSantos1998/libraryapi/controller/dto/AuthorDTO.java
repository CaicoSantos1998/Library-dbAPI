package io.github.CaicoSantos1998.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        @NotBlank(message = "Required field!")
        @Size(min = 3, max = 100, message = "Minimum number of characters not reached or Maximum number of characters reached!")
        String name,

        @NotNull(message = "Required field!")
        @Past(message = "You date of birth cannot be in the future!")
        LocalDate dateBirth,

        @NotBlank(message = "Required field!")
        @Size( min = 6, max = 50, message = "Minimum number of characters not reached or Maximum number characters reached!")
        String nationality) {
}
