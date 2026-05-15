package co.edu.uptc.paymentgateway.exception.domain;

import org.springframework.http.HttpStatus;

import co.edu.uptc.paymentgateway.exception.core.ErrorCode;
import co.edu.uptc.paymentgateway.exception.core.ServerException;

public class InvalidTransactionOwnershipException extends ServerException {
    public InvalidTransactionOwnershipException() {
        super(
                "Una o más transacciones no pertenecen al merchant indicado",
                ErrorCode.INVALID_TRANSACTION_OWNERSHIP,
                HttpStatus.BAD_REQUEST
        );
    }
    
}
