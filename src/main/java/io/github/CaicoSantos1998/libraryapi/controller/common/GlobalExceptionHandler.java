package io.github.CaicoSantos1998.libraryapi.controller.common;

import io.github.CaicoSantos1998.libraryapi.controller.dto.FieldError;
import io.github.CaicoSantos1998.libraryapi.controller.dto.ResponseError;
import io.github.CaicoSantos1998.libraryapi.exceptions.FieldInvalidatedException;
import io.github.CaicoSantos1998.libraryapi.exceptions.OperationNotPermittedException;
import io.github.CaicoSantos1998.libraryapi.exceptions.RegisterDuplicatedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<org.springframework.validation.FieldError> fieldErrors = e.getFieldErrors();
        List<FieldError> fieldErrorList = fieldErrors
                .stream()
                .map(fe -> new FieldError(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseError(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Error of validation", fieldErrorList);
    }
    @ExceptionHandler(RegisterDuplicatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handleRegisterDuplicatedException(RegisterDuplicatedException e) {
        return ResponseError.conflict(e.getMessage());
    }

    @ExceptionHandler(OperationNotPermittedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseError handlerOperationNotPermittedException(OperationNotPermittedException e) {
        return ResponseError.responseStandard(e.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseError handlerDataIntegrityViolation(DataIntegrityViolationException e) {
        String message = "ISBN already registered!";
        return ResponseError.conflict(message);
    }

    @ExceptionHandler(FieldInvalidatedException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseError handlerFieldInvalidatedException(FieldInvalidatedException e) {
        return new ResponseError(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Error of validation",
                List.of(new FieldError(e.getField(), e.getMessage())));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseError handlerErrors(RuntimeException e) {
        return new ResponseError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred! SORRY", List.of());
    }
}
