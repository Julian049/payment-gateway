package co.edu.uptc.paymentgateway.service.impl;

import co.edu.uptc.paymentgateway.client.MastercardApiClient;
import co.edu.uptc.paymentgateway.client.VisaApiClient;
import co.edu.uptc.paymentgateway.exception.domain.*;
import co.edu.uptc.paymentgateway.mapper.TransactionMapper;
import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.model.enums.LiquidationStatus;
import co.edu.uptc.paymentgateway.model.enums.TransactionStatus;
import co.edu.uptc.paymentgateway.repository.MerchantRepository;
import co.edu.uptc.paymentgateway.repository.TransactionRepository;
import co.edu.uptc.paymentgateway.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import java.time.OffsetDateTime;

@AllArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final MerchantRepository merchantRepository;
    private final TransactionMapper mapper;
    private final VisaApiClient visaApiClient;
    private final MastercardApiClient mastercardApiClient;

    @Override
    public TransactionResponseDTO executePayment(TransactionRequestDTO dto) {
        if (!merchantRepository.existsById(dto.getMerchantId())) {
            throw new MerchantNotFoundException(dto.getMerchantId());
        }
        validateCardNumber(dto.getCardNumber(), dto.getCvv());
        Transaction transaction = createInitialTransaction(dto);
        processRouting(transaction, dto.getCardNumber(), dto.getCvv());
        transactionRepository.save(transaction);
        return mapper.toResponseDTO(transaction);

    }

    private Transaction createInitialTransaction(TransactionRequestDTO dto) {
        Transaction entity = mapper.toEntity(dto);
        entity.setTransactionStatus(TransactionStatus.PENDIENTE);
        entity.setLiquidationStatus(LiquidationStatus.NO_LIQUIDADO);
        entity.setTransactionDate(OffsetDateTime.now());
        return transactionRepository.save(entity);
    }

    private void processRouting(Transaction transaction, String cardNumber, String cvv) {
        char identifier = cardNumber.charAt(0);
        boolean authorized;
        try {
            if (identifier == '4') {
                transaction.setCardBrand("Visa");
                authorized = visaApiClient.getVisaDTO(cardNumber, cvv).authorized();
            } else if (identifier == '5') {
                transaction.setCardBrand("Mastercard");
                authorized = mastercardApiClient.getMastercardDTO(cardNumber, cvv).authorized();
            } else {
                throw new UnsupportedCardBrandException(cardNumber);
            }
        } catch (ResourceAccessException ex) {
            throw new PaymentTimeoutException();
        } catch (HttpClientErrorException ex) {
            throw new InvalidCardException("La tarjeta fue rechazada por el proveedor");
        } catch (HttpServerErrorException ex) {
            throw new PaymentProviderException();
        } catch (RestClientException ex) {
            throw new PaymentProviderException();
        }

        transaction.setTransactionStatus(verifyPay(authorized));
    }

    private TransactionStatus verifyPay(boolean isApproved) {
        if (isApproved) {
            return TransactionStatus.APROBADO;
        } else {
            return TransactionStatus.RECHAZADO;
        }
    }

    private void validateCardNumber(String cardNumber, String cvv) {
        if (cardNumber == null || cardNumber.length() < 16) {
            throw new InvalidCardException("Número de tarjeta inválido");
        }

        if (cvv == null || cvv.length() != 3) {
            throw new InvalidCardException("CVV inválido");
        }
    }
}