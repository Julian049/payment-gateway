package co.edu.uptc.paymentgateway.controller;

import co.edu.uptc.paymentgateway.model.dto.TransactionDTO;
import co.edu.uptc.paymentgateway.service.TransactionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment-gateway")
public class TransactionController {
    private final TransactionService service;


    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping
    public TransactionDTO executePayment(@RequestBody TransactionDTO transactionDTO) {
        return  service.executePayment(transactionDTO);
    }

}