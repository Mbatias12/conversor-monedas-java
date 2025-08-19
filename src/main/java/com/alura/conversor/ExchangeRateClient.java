package com.alura.conversor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ExchangeRateClient {
    private final HttpClient http = HttpClient.newHttpClient();
    private final String apiKey;

    public ExchangeRateClient(String apiKey) {
        if (apiKey == null || apiKey.isBlank())
            throw new IllegalArgumentException("Falta API key (EXCHANGE_RATE_API_KEY).");
        this.apiKey = apiKey;
    }

    /** Convierte usando el endpoint 'pair/{FROM}/{TO}/{AMOUNT}' y devuelve el conversion_result. */
    public BigDecimal convert(String from, String to, BigDecimal amount) throws IOException, InterruptedException {
        if (amount == null || amount.signum() <= 0) throw new IllegalArgumentException("El monto debe ser > 0.");

        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/pair/" + from + "/" + to + "/" + amount;

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() < 200 || response.statusCode() >= 300)
            throw new IOException("HTTP " + response.statusCode() + " al consultar la API.");

        JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
        String result = json.get("result").getAsString();
        if (!"success".equalsIgnoreCase(result)) {
            String err = json.has("error-type") ? json.get("error-type").getAsString() : "unknown-error";
            throw new IOException("Error de API: " + err);
        }
        return json.get("conversion_result").getAsBigDecimal();
    }
}