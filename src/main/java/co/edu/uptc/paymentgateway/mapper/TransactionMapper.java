package co.edu.uptc.paymentgateway.mapper;

import co.edu.uptc.paymentgateway.model.dto.TransactionDTO;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction transactionDTOToTransaction(TransactionDTO transactionDTO);
    TransactionDTO transactionToTransactionDTO(Transaction transaction);
}