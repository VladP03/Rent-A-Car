package com.rentacar.service.exceptions;

import com.rentacar.service.exceptions.car.*;
import com.rentacar.service.exceptions.city.CityNotFoundException;
import com.rentacar.service.exceptions.country.CountryNotFoundException;
import com.rentacar.service.exceptions.dataIntegrity.NameUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.EmailUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.PhoneNumberUniqueConstraintException;
import com.rentacar.service.exceptions.dataIntegrity.VinUniqueConstraintException;
import com.rentacar.service.exceptions.dealership.DealershipCityInvalidException;
import com.rentacar.service.exceptions.dealership.DealershipNotFoundException;
import com.rentacar.service.exceptions.rent.RentCarIndisponible;
import com.rentacar.service.exceptions.rent.RentDateInvalidException;
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
        return new ResponseEntity<>(new ApiError(status, ex.getMessage(), ""), status);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onConstraintViolationException(ConstraintViolationException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), "");
    }

    /* Unique Exceptions */

    @ExceptionHandler(EmailUniqueConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onEmailConstraintException(EmailUniqueConstraintException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(NameUniqueConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onNameUniqueConstraintException(NameUniqueConstraintException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(PhoneNumberUniqueConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onPhoneNumberUniqueConstrantException(PhoneNumberUniqueConstraintException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(VinUniqueConstraintException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onVinUniqueConstraintException(VinUniqueConstraintException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }


    /* Rent Exceptions */

    @ExceptionHandler(RentDateInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onRentDateInvalidException(RentDateInvalidException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(RentCarIndisponible.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onRentCarIndisponible(RentCarIndisponible exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }


    /* Dealership Exceptions */

    @ExceptionHandler(DealershipCityInvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onDealershipCityInvalidException(DealershipCityInvalidException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(DealershipNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onDealershipNotFoundException(DealershipNotFoundException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }


    /* Country Expcetions */

    @ExceptionHandler(CountryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError onCountryNotFoundException(CountryNotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception.getMessage(), exception.getDebugMessage());
    }


    /* City Expcetions */

    @ExceptionHandler(CityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError onCityNotFoundException(CityNotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception.getMessage(), exception.getDebugMessage());
    }


    /* Car Expcetions */

    @ExceptionHandler(CarNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ApiError onCarNotFoundException(CarNotFoundException exception) {
        return new ApiError(HttpStatus.NOT_FOUND, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(CarFirstRegistrationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarFirstRegistrationException(CarFirstRegistrationException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(CarFuelException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarFuelException(CarFuelException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }

    @ExceptionHandler(CarGearboxException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ApiError onCarGearboxException(CarGearboxException exception) {
        return new ApiError(HttpStatus.BAD_REQUEST, exception.getMessage(), exception.getDebugMessage());
    }
}
