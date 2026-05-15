package co.edu.uptc.paymentgateway.exception.core;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNSUPPORTED_CARD_BRAND("PAY-401", "La franquicia de la tarjeta no es soportada"),
    PAYMENT_TIMEOUT("PAY-402", "Tiempo de espera agotado con el proveedor de pago"),
    PAYMENT_PROVIDER_ERROR("PAY-403", "Error al comunicarse con el proveedor de pago"),
    MERCHANT_NOT_FOUND("PAY-404", "la empresa no fue encontrada"),
    INVALID_CARD("PAY-405", "La tarjeta es inválida"),
    INVALID_MERCHANT("PAY-406", "El ID de la empresa no es valido, verifique el formato"),
    INVALID_DATE_RANGE("PAY-407", "El rango de fechas es invalido"),
    INVALID_TRANSACTION_OWNERSHIP("PAY-408", "Transacciones no válidas para este merchant");


    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }
}
