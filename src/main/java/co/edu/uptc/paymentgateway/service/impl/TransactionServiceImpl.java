package co.edu.uptc.paymentgateway.service.impl;

import co.edu.uptc.paymentgateway.client.MastercardApiClient;
import co.edu.uptc.paymentgateway.client.VisaApiClient;
import co.edu.uptc.paymentgateway.mapper.TransactionMapper;
import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.external.VisaDTO;
import co.edu.uptc.paymentgateway.model.entity.Transaction;
import co.edu.uptc.paymentgateway.model.enums.LiquidationStatus;
import co.edu.uptc.paymentgateway.model.enums.TransactionStatus;
import co.edu.uptc.paymentgateway.repository.MerchantRepository;
import co.edu.uptc.paymentgateway.repository.TransactionRepository;
import co.edu.uptc.paymentgateway.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

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
        VisaDTO response = visaApiClient.getVisaDTO(dto.getCardNumber(), dto.getCvv());
        System.out.println("Respuesta" + response);
        System.out.println(response.authorized());

        if (merchantRepository.existsById(dto.getMerchantId())) {
            Transaction transaction = createInitialTransaction(dto);
            //Esto es para verificar si si se guarda la primera vez con el estado pendiente
            try { Thread.sleep(10000); } catch (Exception e) {}
            processRouting(transaction, dto.getCardNumber(), dto.getCvv());
            transactionRepository.save(transaction);
            return mapper.toResponseDTO(transaction);
        } else {
            return null;
        }
    }

    private Transaction createInitialTransaction(TransactionRequestDTO dto) {
        Transaction entity = mapper.toEntity(dto);
        entity.setTransactionStatus(TransactionStatus.PENDIENTE);
        entity.setLiquidationStatus(LiquidationStatus.NO_LIQUIDADO);
        entity.setTransactionDate(OffsetDateTime.now());
        System.out.println("Primera transaccion" + entity);
        return transactionRepository.save(entity);
    }

    private void processRouting(Transaction transaction,String cardNumber, String cvv) {
        char identifier = cardNumber.charAt(0);
        boolean authorized = false;
        if (identifier == '4') {
            transaction.setCardBrand("Visa");
            authorized = visaApiClient.getVisaDTO(cardNumber, cvv).authorized();
        } else if (identifier == '5') {
            transaction.setCardBrand("Mastercard");
            authorized = mastercardApiClient.getMastercardDTO(cardNumber, cvv).authorized();
        }

        transaction.setTransactionStatus(verifyPay(authorized));
        System.out.println("Segunda transaccion" + transaction);
    }

    private TransactionStatus verifyPay(boolean isApproved) {
        if (isApproved) {
            return TransactionStatus.APROBADO;
        } else {
            return TransactionStatus.RECHAZADO;
        }
    }

}