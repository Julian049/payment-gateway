package co.edu.uptc.paymentgateway.model.dto;

import co.edu.uptc.paymentgateway.model.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionResponseDTO {
    private UUID transactionId;
    private TransactionStatus status;
    private String date;
}