package io.github.kriolos.ewallet.mpesa;

public class Mpesa {

    private static MpesaConfig config;

    // Initializes the configuration and returns a new Transaction instance
    public static Transaction init(String apiKey, String publicKey, String environment) {
        config = new MpesaConfig(apiKey, publicKey, environment);
        return new Transaction();
    }

    // Package-private getter to allow Request to fetch the current config safely
    static MpesaConfig getConfig() {
        if (config == null) {
            throw new IllegalStateException("Mpesa SDK has not been initialized. Call init() first.");
        }
        return config;
    }
}
