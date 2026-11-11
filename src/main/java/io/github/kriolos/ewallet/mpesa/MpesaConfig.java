package io.github.kriolos.ewallet.mpesa;

// Configuration record to safely hold API state
public record MpesaConfig(String apiKey, String publicKey, String environment) {}
