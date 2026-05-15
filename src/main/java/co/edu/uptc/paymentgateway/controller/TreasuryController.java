package co.edu.uptc.paymentgateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.paymentgateway.model.dto.LiquidationRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.LiquidationResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.TreasuryResponseDTO;
import co.edu.uptc.paymentgateway.service.TreasuryService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/treasury")
@AllArgsConstructor
public class TreasuryController {
    private final TreasuryService service;

    @GetMapping("/transactions")
    public TreasuryResponseDTO getUnliquidatedTransactions(
            @RequestParam UUID merchantId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        OffsetDateTime start = startDate != null ? startDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime() : null;
        OffsetDateTime end = endDate != null ? endDate.atTime(23, 59, 59).atOffset(ZoneOffset.UTC) : null;

        return service.getUnliquidatedTransactions(merchantId, start, end);
    }
    
    @PostMapping("/liquidate")
    public LiquidationResponseDTO liquidate(@RequestBody LiquidationRequestDTO request) {
        return service.liquidate(request);
    }
    
    
}
