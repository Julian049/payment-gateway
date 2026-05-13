package co.edu.uptc.paymentgateway.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TransactionRequestDTO {
    private int cardNumber;
    private double amount;
    private UUID merchantId;
    private String cardBrand;
}
