package co.edu.uptc.paymentgateway.model.dto;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiquidationRequestDTO {
    private UUID merchantId;
    private List<UUID> transactionIds;
}
