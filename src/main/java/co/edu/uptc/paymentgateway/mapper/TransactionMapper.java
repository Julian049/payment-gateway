package co.edu.uptc.paymentgateway.mapper;

import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(source = "merchantId", target = "merchantId.id")
    Transaction toEntity(TransactionRequestDTO requestDTO);
    @Mapping(source = "id", target = "transactionId")
    @Mapping(source = "transactionStatus", target = "status")
    @Mapping(source = "transactionDate", target = "date")
    TransactionResponseDTO toResponseDTO(Transaction transaction);

    default String map(OffsetDateTime date) {
        if (date == null) return null;
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}