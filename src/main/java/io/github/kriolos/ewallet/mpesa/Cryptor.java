package io.github.kriolos.ewallet.mpesa;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.crypto.Cipher;

public class Cryptor {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /**
     * Generates the M-Pesa authentication token by encrypting the API Key with the Public Key.
     * Aligned with the official Vodacom M-Pesa Developer Portal specification.
     */
    public static String token(String publicKeyBase64, String apiKey) throws Exception {
        // Safe decoding using modern Java 17 native Base64 utility instead of Apache Commons
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64.trim());

        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(spec);

        // Instantiated as "RSA" to perfectly match the official SDK provider configuration
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        byte[] encryptedBytes = cipher.doFinal(apiKey.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Generates a random alphanumeric ID for transaction referencing.
     */
    public static String getId(int length) {
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(randomIndex));
        }
        return randomString.toString();
    }

    /**
     * Default overload matching the original PHP implementation behavior.
     */
    public static String getId() {
        return getId(8);
    }
}
