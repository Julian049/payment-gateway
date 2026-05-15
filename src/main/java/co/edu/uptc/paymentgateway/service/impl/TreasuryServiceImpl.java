package co.edu.uptc.paymentgateway.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import co.edu.uptc.paymentgateway.exception.domain.InvalidDateRangeException;
import co.edu.uptc.paymentgateway.exception.domain.InvalidTransactionOwnershipException;
import co.edu.uptc.paymentgateway.exception.domain.MerchantNotFoundException;
import co.edu.uptc.paymentgateway.mapper.TransactionMapper;
import co.edu.uptc.paymentgateway.model.dto.LiquidationRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.LiquidationResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.TreasuryResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.TreasuryTransactionDTO;
import co.edu.uptc.paymentgateway.model.entity.Merchant;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.model.enums.LiquidationStatus;
import co.edu.uptc.paymentgateway.repository.MerchantRepository;
import co.edu.uptc.paymentgateway.repository.TransactionRepository;
import co.edu.uptc.paymentgateway.service.TreasuryService;
import co.edu.uptc.paymentgateway.specification.TransactionSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TreasuryServiceImpl implements TreasuryService {
    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionMapper mapper;

    @Override
    public TreasuryResponseDTO getUnliquidatedTransactions(UUID merchantId, OffsetDateTime startDate, OffsetDateTime endDate) {
        
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new InvalidDateRangeException("La fecha de inicio no puede ser posterior a la fecha de fin.");
        }
        
        Merchant merchant = merchantRepository.findById(merchantId)
                .orElseThrow(() -> new MerchantNotFoundException(merchantId));

        List<Transaction> transactions = transactionRepository.findAll(
            TransactionSpecification.unliquidated(merchantId, startDate, endDate));

        List<TreasuryTransactionDTO> dtos = transactions.stream()
                .map(mapper::toTreasuryTransactionDTO)
                .toList();

        double total = transactions.stream().mapToDouble(Transaction::getAmount).sum();

        TreasuryResponseDTO response = new TreasuryResponseDTO();
        response.setMerchantId(merchantId);
        response.setMerchantName(merchant.getName());
        response.setTotalBalance(total);
        response.setTransactions(dtos);

        return response;
    }

    @Override
    @Transactional
    public LiquidationResponseDTO liquidate(LiquidationRequestDTO request) {
        List<Transaction> transactions = transactionRepository
            .findAllByIdInAndMerchantIdIdAndLiquidationStatus(
                request.getTransactionIds(), 
                request.getMerchantId(), 
                LiquidationStatus.NO_LIQUIDADO);

        if (transactions.size() != request.getTransactionIds().size()) {
            throw new InvalidTransactionOwnershipException();
        }

        List<UUID> ids = transactions.stream().map(Transaction::getId).toList();
        transactionRepository.liquidateByIds(ids);

        LiquidationResponseDTO response = new LiquidationResponseDTO();
        response.setLiquidated(ids.size());
        response.setMessage(ids.size() + " transacciones liquidadas correctamente");
        return response;
    }

}
