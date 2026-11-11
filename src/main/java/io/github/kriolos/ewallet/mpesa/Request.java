package io.github.kriolos.ewallet.mpesa;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.stream.Collectors;

public class Request {

    private final HttpClient httpClient;
    private final String apiKey;
    private final String publicKey;

    public Request() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();
        
        MpesaConfig config = Mpesa.getConfig();
        this.apiKey = config.apiKey();
        this.publicKey = config.publicKey();
    }

    // Executes an HTTP GET request with query parameters
    public String get(String urlPath, Map<String, String> params) throws Exception {
        String queryString = buildQueryString(params);
        String fullUrl = queryString.isEmpty() ? urlPath : urlPath + "?" + queryString;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(fullUrl))
                .GET()
                .headers(getHeaders())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Executes an HTTP POST request with a JSON payload string
    public String post(String urlPath, String jsonParams) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlPath))
                .POST(HttpRequest.BodyPublishers.ofString(jsonParams, StandardCharsets.UTF_8))
                .headers(getHeaders())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Executes an HTTP PUT request with a JSON payload string
    public String put(String urlPath, String jsonParams) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlPath))
                .PUT(HttpRequest.BodyPublishers.ofString(jsonParams, StandardCharsets.UTF_8))
                .headers(getHeaders())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    // Prepares the shared headers array required for M-Pesa API requests
    private String[] getHeaders() throws Exception {
        String token = Cryptor.token(this.publicKey, this.apiKey);
        return new String[] {
            "Content-Type", "application/json",
            "Origin", "*",
            "Authorization", "Bearer " + token
        };
    }

    // Converts a map of parameters to a URL-encoded query string
    private String buildQueryString(Map<String, String> params) {
        if (params == null || params.isEmpty()) {
            return "";
        }
        return params.entrySet().stream()
                .map(entry -> URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8) + "=" +
                              URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8))
                .collect(Collectors.joining("&"));
    }
}
