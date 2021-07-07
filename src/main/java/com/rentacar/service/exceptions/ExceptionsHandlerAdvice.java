package com.rentacar.service.exceptions;

import com.rentacar.service.exceptions.car.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class ExceptionsHandlerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ApiError(status, ex.getMessage()), status);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onConstraintViolationException(ConstraintViolationException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarNotFoundException(CarNotFoundException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CarAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarAlreadyExistsException(CarAlreadyExistsException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CarFirstRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarFirstRegistrationException(CarFirstRegistrationException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CarFuelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarFuelException(CarFuelException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(CarGearboxException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarGearboxException(CarGearboxException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage());
    }
}
