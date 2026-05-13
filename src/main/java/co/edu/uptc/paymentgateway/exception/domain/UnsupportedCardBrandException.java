package co.edu.uptc.paymentgateway.exception.domain;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;
import org.springframework.http.HttpStatus;

public class UnsupportedCardBrandException extends ServerException {

    public UnsupportedCardBrandException(String card) {
        super("El numero de tarjeta: " + card + " no pertenece a las franquicias registradas (Visa/Mastercard)", ErrorCode.UNSUPPORTED_CARD_BRAND, HttpStatus.BAD_REQUEST);
    }
}