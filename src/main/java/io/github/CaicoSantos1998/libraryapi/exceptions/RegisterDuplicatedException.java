package io.github.CaicoSantos1998.libraryapi.exceptions;

public class RegisterDuplicatedException extends RuntimeException {
    public RegisterDuplicatedException(String message) {
        super(message);
    }
}
