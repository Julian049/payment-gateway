package co.edu.uptc.paymentgateway.model.dto;

import java.util.UUID;

import co.edu.uptc.paymentgateway.model.enums.TransactionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreasuryTransactionDTO {
    private UUID id;
    private double amount;
    private String cardBrand;
    private TransactionStatus transactionStatus;
    private String transactionDate;
}
