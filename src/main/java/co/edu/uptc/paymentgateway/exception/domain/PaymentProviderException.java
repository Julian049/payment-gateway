package co.edu.uptc.paymentgateway.exception.domain;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;
import org.springframework.http.HttpStatus;

public class PaymentProviderException extends ServerException {

    public PaymentProviderException(String provider) {
        super(
                "Error al comunicarse con el proveedor: " + provider,
                ErrorCode.PAYMENT_PROVIDER_ERROR,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    public PaymentProviderException() {
        super(
                ErrorCode.PAYMENT_PROVIDER_ERROR.getDescription(),
                ErrorCode.PAYMENT_PROVIDER_ERROR,
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }
}