package co.edu.uptc.paymentgateway.exception.domain;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;
import org.springframework.http.HttpStatus;

public class InvalidCardException extends ServerException {

    public InvalidCardException(String message) {
        super(
                message,
                ErrorCode.INVALID_CARD,
                HttpStatus.BAD_REQUEST
        );
    }
}
