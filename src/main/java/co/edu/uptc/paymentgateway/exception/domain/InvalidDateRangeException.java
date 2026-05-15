package co.edu.uptc.paymentgateway.exception.domain;

import org.springframework.http.HttpStatus;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;


public class InvalidDateRangeException extends ServerException {

    public InvalidDateRangeException(String message) {
        super(
                message,
                ErrorCode.INVALID_DATE_RANGE,
                HttpStatus.BAD_REQUEST
        );
    }
    
}
