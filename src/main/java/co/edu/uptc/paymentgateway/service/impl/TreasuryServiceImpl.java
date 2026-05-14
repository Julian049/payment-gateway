package co.edu.uptc.paymentgateway.service.impl;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import co.edu.uptc.paymentgateway.exception.domain.MerchantNotFoundException;
import co.edu.uptc.paymentgateway.mapper.TransactionMapper;
import co.edu.uptc.paymentgateway.model.dto.TreasuryResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.TreasuryTransactionDTO;
import co.edu.uptc.paymentgateway.model.entity.Merchant;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.repository.MerchantRepository;
import co.edu.uptc.paymentgateway.repository.TransactionRepository;
import co.edu.uptc.paymentgateway.service.TreasuryService;
import co.edu.uptc.paymentgateway.specification.TransactionSpecification;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class TreasuryServiceImpl implements TreasuryService {
    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionMapper mapper;

    @Override
    public TreasuryResponseDTO getUnliquidatedTransactions(UUID merchantId, OffsetDateTime startDate, OffsetDateTime endDate) {
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
}
