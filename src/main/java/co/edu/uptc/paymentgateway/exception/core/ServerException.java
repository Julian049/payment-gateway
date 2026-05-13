package co.edu.uptc.paymentgateway.exception.core;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class ServerException extends RuntimeException {
    private final ErrorCode errorCode;
    private final HttpStatus status;

    public ServerException(String message, ErrorCode errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
