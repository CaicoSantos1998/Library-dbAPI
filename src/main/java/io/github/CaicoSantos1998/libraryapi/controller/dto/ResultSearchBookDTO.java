package io.github.CaicoSantos1998.libraryapi.controller.dto;

import io.github.CaicoSantos1998.libraryapi.model.GenderBook;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record ResultSearchBookDTO(
        UUID id,
        String isbn,
        String title,
        LocalDate datePublication,
        GenderBook gender,
        BigDecimal price,
        AuthorDTO author) {
}
