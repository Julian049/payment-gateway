package co.edu.uptc.paymentgateway.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionRequestDTO {
    private String cardNumber;
    private double amount;
    private String cvv;
    private UUID merchantId;
}
