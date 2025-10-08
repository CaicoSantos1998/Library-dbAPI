package io.github.CaicoSantos1998.libraryapi.exceptions;

import lombok.Getter;

public class fieldInvalidatedException extends RuntimeException {

    @Getter
    private String field;

    public fieldInvalidatedException(String field, String message) {
        super(message);
        this.field=field;
    }
}
