package co.edu.uptc.paymentgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean(name = "mastercardRestClient")
    RestClient mastercardRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://localhost")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean(name = "visaRestClient")
    RestClient visaRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("https://localhost")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}