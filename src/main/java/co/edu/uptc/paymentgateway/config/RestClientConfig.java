package co.edu.uptc.paymentgateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(10000);
        factory.setReadTimeout(15000);
        return factory;
    }

    @Bean(name = "mastercardRestClient")
    RestClient mastercardRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:3002")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestFactory(getClientHttpRequestFactory())
                .build();
    }

    @Bean(name = "visaRestClient")
    RestClient visaRestClient(RestClient.Builder builder) {
        return builder
                .baseUrl("http://localhost:3001")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .requestFactory(getClientHttpRequestFactory())
                .build();
    }
}