package co.edu.uptc.paymentgateway.service.impl;

import co.edu.uptc.paymentgateway.mapper.TransactionMapper;
import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.model.enums.LiquidationStatus;
import co.edu.uptc.paymentgateway.model.enums.TransactionStatus;
import co.edu.uptc.paymentgateway.repository.MerchantRepository;
import co.edu.uptc.paymentgateway.repository.TransactionRepository;
import co.edu.uptc.paymentgateway.service.TransactionService;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionRepository transactionRepository, MerchantRepository merchantRepository, TransactionMapper mapper) {
        this.transactionRepository = transactionRepository;
        this.merchantRepository = merchantRepository;
        this.mapper = mapper;
    }

    @Override
    public TransactionResponseDTO executePayment(TransactionRequestDTO dto) {
        if (merchantRepository.existsById(dto.getMerchantId())) {
            Transaction entity = mapper.toEntity(dto);
            //Aca despues se pone la logica para decidir por donde se va
            entity.setCardBrand("Visa");
            entity.setTransactionStatus(TransactionStatus.PENDIENTE);
            entity.setLiquidationStatus(LiquidationStatus.NO_LIQUIDADO);
            entity.setTransactionDate(OffsetDateTime.now());

            Transaction savedEntity = transactionRepository.save(entity);
            return mapper.toResponseDTO(savedEntity);
        } else {
            return null;
        }
    }
}