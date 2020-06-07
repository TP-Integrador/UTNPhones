package edu.utn.utnphones.controller.web;

import edu.utn.utnphones.dto.ErrorResponseDto;
import edu.utn.utnphones.exception.InvalidLoginException;
import edu.utn.utnphones.exception.PhoneLineNotExistException;
import edu.utn.utnphones.exception.UserNotexistException;
import edu.utn.utnphones.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.text.ParseException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidLoginException.class)
    public ErrorResponseDto handleLoginException(InvalidLoginException exc) {
        return new ErrorResponseDto(1, "Invalid login");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ValidationException.class)
    public ErrorResponseDto handleValidationException(ValidationException exc) {
        return new ErrorResponseDto(2, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotexistException.class)
    public ErrorResponseDto handleNotFoundException(UserNotexistException exc) {
        return new ErrorResponseDto(3, exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(4, "Not valid dates");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineNotExistException.class)
    public ErrorResponseDto handlePhoneLineNotFoundException(PhoneLineNotExistException exc) {
        return new ErrorResponseDto(4, "PhoneLine not exists: " + exc.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(SQLException.class)
    public ErrorResponseDto handleSQLException(SQLException exc) {
        return new ErrorResponseDto(5, "Internal error server");
    }

}
