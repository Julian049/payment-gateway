package co.edu.uptc.paymentgateway.model.dto.external;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MastercardDTO (
        @JsonProperty("permitido")
        boolean authorized,

        @JsonProperty("descripcion")
        String message
) {}