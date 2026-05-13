package co.edu.uptc.paymentgateway.client;

import co.edu.uptc.paymentgateway.config.RestClientConfig;
import co.edu.uptc.paymentgateway.model.dto.external.MastercardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {MastercardApiClient.class, RestClientConfig.class})
@ImportAutoConfiguration(RestClientAutoConfiguration.class)
class MastercardApiClientTest {

    @Autowired
    private MastercardApiClient mastercardApiClient;

    @Test
    void getMastercardDTO() {
        MastercardDTO result = mastercardApiClient.getMastercardDTO("5000000000000000", "123");

        System.out.println(result);

        assertNotNull(result);
        assertThat(result.authorized()).isTrue();
        assertThat(result.message()).isEqualTo("Tarjeta validada correctamente");
    }

}