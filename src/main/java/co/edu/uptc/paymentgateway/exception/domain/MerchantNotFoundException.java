package co.edu.uptc.paymentgateway.exception.domain;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class MerchantNotFoundException extends ServerException {

    public MerchantNotFoundException(UUID merchantId) {
        super(
                "No existe una empresa con id: " + merchantId,
                ErrorCode.MERCHANT_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }
}
