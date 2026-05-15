package co.edu.uptc.paymentgateway.exception.handler;

import co.edu.uptc.paymentgateway.exception.core.ServerException;
import co.edu.uptc.paymentgateway.exception.domain.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidTransactionOwnershipException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidTransactionOwnership(
        InvalidTransactionOwnershipException ex) {

    log.warn("InvalidTransactionOwnershipException: {}", ex.getMessage());
    return buildResponse(ex);
}

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidDateRange(
        InvalidDateRangeException ex) {

        log.warn("InvalidDateRangeException: {}", ex.getMessage());
        return buildResponse(ex);
    }
    @ExceptionHandler(UnsupportedCardBrandException.class)
    public ResponseEntity<Map<String, Object>> handleUnsupportedCardBrand(
            UnsupportedCardBrandException ex) {

        log.warn("UnsupportedCardBrandException: {}", ex.getMessage());
        return buildResponse(ex);
    }

    @ExceptionHandler(PaymentTimeoutException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentTimeout(
            PaymentTimeoutException ex) {

        log.error("PaymentTimeoutException: {}", ex.getMessage(), ex);
        return buildResponse(ex);
    }

    @ExceptionHandler(PaymentProviderException.class)
    public ResponseEntity<Map<String, Object>> handlePaymentProvider(
            PaymentProviderException ex) {

        log.error("PaymentProviderException: {}", ex.getMessage(), ex);
        return buildResponse(ex);
    }

    @ExceptionHandler(MerchantNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMerchantNotFound(
            MerchantNotFoundException ex) {

        log.warn("MerchantNotFoundException: {}", ex.getMessage());
        return buildResponse(ex);
    }

    @ExceptionHandler(InvalidCardException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCard(
            InvalidCardException ex) {

        log.warn("InvalidCardException: {}", ex.getMessage());
        return buildResponse(ex);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidJson(
            HttpMessageNotReadableException ex
    ) {

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException invalidFormat) {

            String fieldName = invalidFormat.getPath().stream()
                    .findFirst()
                    .map(JsonMappingException.Reference::getFieldName)
                    .orElse("");

            if ("merchantId".equals(fieldName)) {

                return buildResponse(
                        new InvalidMerchantException("ID invalido")
                );
            }
        }

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "INVALID_JSON");
        body.put("message", "El cuerpo de la petición contiene datos inválidos");

        return ResponseEntity
                .badRequest()
                .body(body);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {

        log.error("Error inesperado: {}", ex.getMessage(), ex);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "INTERNAL_SERVER_ERROR");
        body.put("message", "Ocurrió un error inesperado.");

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(body);
    }

    private ResponseEntity<Map<String, Object>> buildResponse(ServerException ex) {

        Map<String, Object> body = new LinkedHashMap<>();

        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatus().value());
        body.put("error", ex.getErrorCode().getCode());
        body.put("description", ex.getErrorCode().getDescription());
        body.put("message", ex.getMessage());

        return ResponseEntity
                .status(ex.getStatus())
                .body(body);
    }
}