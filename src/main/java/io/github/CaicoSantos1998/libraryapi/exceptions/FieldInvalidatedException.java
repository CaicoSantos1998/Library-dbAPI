package io.github.CaicoSantos1998.libraryapi.exceptions;

import lombok.Getter;

public class FieldInvalidatedException extends RuntimeException {

    @Getter
    private String field;

    public FieldInvalidatedException(String field, String message) {
        super(message);
        this.field=field;
    }
}
