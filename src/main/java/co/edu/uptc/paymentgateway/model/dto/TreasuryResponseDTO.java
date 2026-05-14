package co.edu.uptc.paymentgateway.model.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TreasuryResponseDTO {
    private UUID merchantId;
    private String merchantName;
    private double totalBalance;
    private List<TreasuryTransactionDTO> transactions;
}
