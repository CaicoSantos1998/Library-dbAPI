package io.github.CaicoSantos1998.libraryapi.controller.dto;

import io.github.CaicoSantos1998.libraryapi.model.Author;
import io.github.CaicoSantos1998.libraryapi.model.GenderBook;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookDTO(
        @NotNull(message = "This field cannot be empty! Is mandatory!")
        @Size(max = 20, message = "ISBN cannot be larger what 20 characters!")
        String isbn,

        @NotBlank(message = "This field is mandatory!")
        @Size(max = 150, message = "The title cannot be larger what 150 characters!")
        String title,

        @NotNull(message = "This field is mandatory!")
        @Past(message = "This date cannot be of future")
        LocalDate datePublication,
        Enum<GenderBook> gender,
        BigDecimal price,
        Author idAuthor) {
}
