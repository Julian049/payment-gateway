package co.edu.uptc.paymentgateway.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.uptc.paymentgateway.model.dto.LiquidationRequestDTO;
import co.edu.uptc.paymentgateway.model.dto.LiquidationResponseDTO;
import co.edu.uptc.paymentgateway.model.dto.TreasuryResponseDTO;
import co.edu.uptc.paymentgateway.service.TreasuryService;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/treasury")
@AllArgsConstructor
public class TreasuryController {

    private final TreasuryService service;

    @GetMapping("/transactions")
    @Operation(summary = "Consultar transacciones no liquidadas", description = "Retorna las transacciones aprobadas y no liquidadas de un merchant, con filtro opcional de fechas.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida correctamente"),

            @ApiResponse(responseCode = "400", description = "Rango de fechas inválido",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Fechas inválidas", value = "{\"code\": \"PAY-407\", \"error\": \"El rango de fechas es inválido\", \"message\": \"La fecha de inicio no puede ser posterior a la fecha de fin\"}")
                    })),

            @ApiResponse(responseCode = "404", description = "Merchant no encontrado",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Merchant no existe", value = "{\"code\": \"PAY-404\", \"error\": \"la empresa no fue encontrada\", \"message\": \"No existe una empresa con id: 123e4567...\"}")
                    }))
    })
    public TreasuryResponseDTO getUnliquidatedTransactions(
            @RequestParam UUID merchantId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        OffsetDateTime start = startDate != null ? startDate.atStartOfDay(ZoneOffset.UTC).toOffsetDateTime() : null;
        OffsetDateTime end = endDate != null ? endDate.atTime(23, 59, 59).atOffset(ZoneOffset.UTC) : null;

        return service.getUnliquidatedTransactions(merchantId, start, end);
    }

    @PostMapping("/liquidate")
    @Operation(summary = "Liquidar transacciones", description = "Marca como liquidadas una lista de transacciones pertenecientes al merchant indicado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transacciones liquidadas correctamente"),

            @ApiResponse(responseCode = "400", description = "Formato de datos inválido",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "JSON inválido", value = "{\"error\": \"INVALID_JSON\", \"message\": \"El cuerpo de la petición contiene datos inválidos\"}")
                    })),

            @ApiResponse(responseCode = "404", description = "Transacciones no encontradas o ya liquidadas",
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(name = "Transacciones inválidas", value = "{\"code\": \"PAY-408\", \"error\": \"Transacciones no válidas para este merchant\", \"message\": \"Una o más transacciones no pertenecen al merchant indicado o ya fueron liquidadas\"}")
                    }))
    })
    public LiquidationResponseDTO liquidate(@RequestBody LiquidationRequestDTO request) {
        return service.liquidate(request);
    }
}
