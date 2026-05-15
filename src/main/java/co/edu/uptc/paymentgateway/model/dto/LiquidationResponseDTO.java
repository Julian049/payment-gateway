package co.edu.uptc.paymentgateway.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LiquidationResponseDTO {
    private int liquidated;
    private String message;
}