package co.edu.uptc.paymentgateway.exception.domain;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;
import org.springframework.http.HttpStatus;

public class InvalidMerchantException extends ServerException {

    public InvalidMerchantException(String message) {
        super(
                message,
                ErrorCode.INVALID_MERCHANT,
                HttpStatus.BAD_REQUEST
        );
    }
}