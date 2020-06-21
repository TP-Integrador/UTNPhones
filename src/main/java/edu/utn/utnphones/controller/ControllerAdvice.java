package edu.utn.utnphones.controller;

import edu.utn.utnphones.dto.ErrorResponseDto;
import edu.utn.utnphones.exception.*;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.DateTimeException;

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
        return new ErrorResponseDto(3, "User not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ParseException.class)
    public ErrorResponseDto handleParseException() {
        return new ErrorResponseDto(4, "Not valid dates");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineNotExistException.class)
    public ErrorResponseDto handlePhoneLineNotFoundException(PhoneLineNotExistException exc) {
        return new ErrorResponseDto(5, "PhoneLine not exists" + exc.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineRemovedException.class)
    public ErrorResponseDto handlePhoneLineRemovedException(PhoneLineRemovedException exc) {
        return new ErrorResponseDto(6, "PhoneLine already removed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PhoneLineAlreadyExistsException.class)
    public ErrorResponseDto handlePhoneLineAlreadyExistsException(PhoneLineAlreadyExistsException exc) {
        return new ErrorResponseDto(7, "PhoneLine already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientNotExistsException.class)
    public ErrorResponseDto handleClientNotExistsException(ClientNotExistsException exc) {
        return new ErrorResponseDto(8, "Client not exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientRemovedException.class)
    public ErrorResponseDto handleClientRemovedException(ClientRemovedException exc) {
        return new ErrorResponseDto(9, "Client already removed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClientDniAlreadyExists.class)
    public ErrorResponseDto handleClientDniAlreadyExists(ClientDniAlreadyExists exc) {
        return new ErrorResponseDto(10, "DNI already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNameAlreadyExists.class)
    public ErrorResponseDto handleUserNameAlreadyExists(UserNameAlreadyExists exc) {
        return new ErrorResponseDto(11, "Username already exists");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(StatusNotExistsException.class)
    public ErrorResponseDto handleStatusNotExistsException(StatusNotExistsException exc) {
        return new ErrorResponseDto(12, "Status not allowed");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponseDto handleIllegalArgumentException(IllegalArgumentException exc) {
        return new ErrorResponseDto(13, exc.getMessage() + " . " + exc.toString()+ " . " + exc.getLocalizedMessage()+ " . " + exc.getCause().getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(GenericJDBCException.class)
    public ErrorResponseDto handleDBException(GenericJDBCException exc) {
        return new ErrorResponseDto(14, exc.getSQLException().getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(DateTimeException.class)
    public ErrorResponseDto handleDateTimeException(DateTimeException exc) {
        return new ErrorResponseDto(15, exc.getMessage());
    }


}
