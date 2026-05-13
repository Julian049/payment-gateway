package co.edu.uptc.paymentgateway.mapper;

import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    Transaction toEntity(TransactionRequestDTO requestDTO);
    @Mapping(source = "id", target = "transactionId")
    @Mapping(source = "transactionStatus", target = "status")
    @Mapping(source = "transactionDate", target = "date", dateFormat = "yyyy-MM-dd HH:mm:ss")
    TransactionResponseDTO toResponseDTO(Transaction transaction);
}