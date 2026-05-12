package co.edu.uptc.paymentgateway.client;

import co.edu.uptc.paymentgateway.model.dto.external.MastercardDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestClient;

import java.util.Map;

public class MastercardApiClient {
    private final RestClient restClient;

    public MastercardApiClient(@Qualifier("mastercardRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public MastercardDTO getMastercardDTO(int number, byte cvv) {
        Map<String, Object> body = Map.of(
                "numero_tarjeta", number,
                "cvv", cvv
        );

        return restClient.post().uri("/validar").body(body).retrieve().body(MastercardDTO.class);
    }
}
