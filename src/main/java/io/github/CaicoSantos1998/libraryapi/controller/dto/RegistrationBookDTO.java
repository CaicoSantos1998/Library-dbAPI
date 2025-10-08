package io.github.CaicoSantos1998.libraryapi.controller.dto;

import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record RegistrationBookDTO(
        @ISBN
        String isbn,

        @NotBlank(message = "Required field!")
        String title,

        @NotNull(message = "Required field!")
        @Past(message = "This date cannot be of future")
        LocalDate datePublication,
        GenderBook gender,
        BigDecimal price,
        @NotNull(message = "Required field!")
        UUID idAuthor) {
}
