package co.edu.uptc.paymentgateway.client;

import co.edu.uptc.paymentgateway.model.dto.external.MastercardDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class MastercardApiClient {
    private final RestClient restClient;

    public MastercardApiClient(@Qualifier("mastercardRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public MastercardDTO getMastercardDTO(String number, String cvv) {
        Map<String, Object> body = Map.of(
                "pan", number,
                "codigo_seguridad", cvv
        );

        return restClient.post().uri("/procesar").body(body).retrieve().body(MastercardDTO.class);
    }
}
