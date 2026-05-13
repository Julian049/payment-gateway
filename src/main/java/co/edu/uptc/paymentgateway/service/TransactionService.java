package co.edu.uptc.paymentgateway.service;

import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;

public interface TransactionService {
    TransactionResponseDTO executePayment(TransactionRequestDTO dto);
}