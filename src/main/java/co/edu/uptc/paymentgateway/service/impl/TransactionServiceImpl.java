package co.edu.uptc.paymentgateway.service.impl;

import co.edu.uptc.paymentgateway.mapper.TransactionMapper;
import co.edu.uptc.paymentgateway.model.dto.TransactionDTO;
import co.edu.uptc.paymentgateway.repository.TransactionRepository;
import co.edu.uptc.paymentgateway.service.TransactionService;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;
    private final TransactionMapper mapper;

    public TransactionServiceImpl(TransactionRepository repository, TransactionMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TransactionDTO executePayment(TransactionDTO dto) {
        return null;
    }
}