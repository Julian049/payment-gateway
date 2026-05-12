package co.edu.uptc.paymentgateway.client;

import co.edu.uptc.paymentgateway.model.dto.external.VisaDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
public class VisaApiClient {
    private final RestClient restClient;

    public VisaApiClient(@Qualifier("visaRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public VisaDTO getVisaDTO(int number, byte cvv) {
        Map<String, Object> body = Map.of(
                "numero_tarjeta", number,
                "cvv", cvv
        );

        return restClient.post().uri("/validar").body(body).retrieve().body(VisaDTO.class);
    }
}
