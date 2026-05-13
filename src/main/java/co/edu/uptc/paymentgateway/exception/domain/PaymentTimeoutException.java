package co.edu.uptc.paymentgateway.exception.domain;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;
import org.springframework.http.HttpStatus;

public class PaymentTimeoutException extends ServerException {
    public PaymentTimeoutException() {
        super(
                ErrorCode.PAYMENT_TIMEOUT.getDescription(),
                ErrorCode.PAYMENT_TIMEOUT,
                HttpStatus.GATEWAY_TIMEOUT
        );
    }
}
