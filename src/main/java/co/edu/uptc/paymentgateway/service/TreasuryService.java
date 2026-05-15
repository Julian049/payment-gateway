package co.edu.uptc.paymentgateway.service;

import java.time.OffsetDateTime;
import java.util.UUID;

import co.edu.uptc.paymentgateway.model.dto.LiquidationRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.LiquidationResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.TreasuryResponseDTO;

public interface TreasuryService {
    TreasuryResponseDTO getUnliquidatedTransactions(UUID merchantId, OffsetDateTime startDate, OffsetDateTime endDate);
    LiquidationResponseDTO liquidate(LiquidationRequestDTO request);
}
