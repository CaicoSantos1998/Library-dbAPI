package io.github.CaicoSantos1998.libraryapi.exceptions;

public class OperationNotPermittedException extends RuntimeException{
    public OperationNotPermittedException(String message) {
        super(message);
    }
}
