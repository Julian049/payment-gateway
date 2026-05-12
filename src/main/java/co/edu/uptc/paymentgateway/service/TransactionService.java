package co.edu.uptc.paymentgateway.service;

import co.edu.uptc.paymentgateway.model.dto.TransactionDTO;

public interface TransactionService {
    TransactionDTO executePayment(TransactionDTO dto);
}