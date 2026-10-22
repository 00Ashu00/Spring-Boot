
package com.example.currency_converter.controller;

import com.example.currency_converter.client.CurrencyApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Locale;
@RestController
public class CurrencyController {

    private final CurrencyApiClient currencyApiClient;
public CurrencyController(CurrencyApiClient currencyApiClient) {
    this.currencyApiClient = currencyApiClient;
}

    @GetMapping("/convertCurrency")
    public String convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double units) {

        double exchangeRate = currencyApiClient.getExchangeRates(fromCurrency, toCurrency);
        double convertedAmount = units * exchangeRate;

        return String.format(
                Locale.US,
                "%s %.2f = %s %.2f",
                fromCurrency, units,
                toCurrency, convertedAmount
        );
    }
/*
    @GetMapping("/testExchangeRates")
    public double testExchangeRates() {
        return currencyApiClient.getExchangeRates();
    }
*/

}


