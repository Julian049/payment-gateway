package co.edu.uptc.paymentgateway.controller;

import co.edu.uptc.paymentgateway.model.dto.TransactionRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.TransactionResponseDTO;
import co.edu.uptc.paymentgateway.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment-gateway")
public class TransactionController {
    private final TransactionService service;


    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Ejecutar un pago", description = "Procesa una transacción con Visa o Mastercard.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacción procesada (Aprobada o Rechazada)"),

            @ApiResponse(responseCode = "400", description = "Datos inválidos en la petición",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Tarjeta Inválida", value = "{\"code\": \"PAY-405\", \"error\": \"La tarjeta es inválida\", \"message\": \"Número de tarjeta inválido\"}"),
                            @ExampleObject(name = "Franquicia no soportada", value = "{\"code\": \"PAY-401\", \"error\": \"La franquicia de la tarjeta no es soportada\", \"message\": \"El numero de tarjeta: 3... no pertenece a las franquicias registradas\"}")
                    })),

            @ApiResponse(responseCode = "404", description = "Comercio no encontrado",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Merchant No Existe", value = "{\"code\": \"PAY-404\", \"error\": \"la empresa no fue encontrada\", \"message\": \"No existe una empresa con id: 123e4567...\"}")
                    })),

            @ApiResponse(responseCode = "503", description = "Error del proveedor de pagos",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Proveedor Caído", value = "{\"code\": \"PAY-403\", \"error\": \"Error al comunicarse con el proveedor de pago\", \"message\": \"Error al comunicarse con el proveedor: Visa\"}")
                    })),

            @ApiResponse(responseCode = "504", description = "Timeout con el proveedor",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Timeout", value = "{\"code\": \"PAY-402\", \"error\": \"Tiempo de espera agotado con el proveedor de pago\", \"message\": \"Tiempo de espera agotado con el proveedor de pago\"}")
                    }))
    })
    public TransactionResponseDTO executePayment(@RequestBody TransactionRequestDTO transactionDTO) {
        return service.executePayment(transactionDTO);
    }

}