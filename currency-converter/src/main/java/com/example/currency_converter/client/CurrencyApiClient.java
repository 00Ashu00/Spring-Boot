
package com.example.currency_converter.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import tools.jackson.databind.JsonNode;

@Component
public class CurrencyApiClient {

    private final RestClient restClient;

    public CurrencyApiClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://api.freecurrencyapi.com")
                .build();
    }
    @Value("${freecurrency.api.key}")
    private String apiKey;
    public double getExchangeRates(String fromCurrency, String toCurrency){
        JsonNode response = restClient.get()
                        .uri(uriBuilder -> uriBuilder
                        .path("/v1/latest")
                        .queryParam("base_currency", fromCurrency)
                        .queryParam("currencies", toCurrency)
                        .build())
                        .header("apikey", apiKey)
                        .retrieve()
                        .body(JsonNode.class);
        return response.get("data").get(toCurrency).asDouble();
    }

}

