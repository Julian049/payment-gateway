package co.edu.uptc.paymentgateway.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {
    private Long transactionId;
    private boolean approved;
}