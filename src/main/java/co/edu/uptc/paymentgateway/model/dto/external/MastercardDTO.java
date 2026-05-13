package co.edu.uptc.paymentgateway.model.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MastercardDTO (
        @JsonProperty("autorizado")
        boolean authorized,

        @JsonProperty("mensaje")
        String message
) {}