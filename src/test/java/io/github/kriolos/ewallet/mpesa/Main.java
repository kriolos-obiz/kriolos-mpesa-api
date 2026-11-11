package io.github.kriolos.ewallet.mpesa;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        try {
            // 1. Configurações fictícias do M-Pesa (Substitua pelas credenciais da Vodacom)
            String publicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAv1x3..."; // Chave Pública em Base64
            String apiKey = "api_key_fornecida_pela_vodacom";
            String environment = "development"; // Use "production" para ambiente real

            LOGGER.info("Inicializando o M-Pesa SDK...");
            
            // 2. Inicializa o SDK e obtém a instância de Transaction
            Transaction mpesa = Mpesa.init(apiKey, publicKeyBase64, environment);

            // =================================================================
            // EXEMPLO 1: Cobrar um Cliente via M-Pesa (C2B Payment)
            // =================================================================
            LOGGER.info("Criando requisição de pagamento C2B...");
            
            C2BRequest c2bRequest = new C2BRequest(
                "10.00",                         // value: Valor da transação em Meticais
                "841234567",                     // clientNumber: Número de telemóvel do cliente (MSISDN)
                "171717",                        // agentId: Service Provider Code (Shortcode do negócio)
                Cryptor.getId(),                 // transactionReference: Referência interna (ex: gerada aleatoriamente)
                "INV-2026-001"                   // thirdPartyReference: Referência do seu sistema (ex: ID da Fatura)
            );

            LOGGER.info("Enviando pedido C2B para a API...");
            String c2bResponse = mpesa.c2b(c2bRequest);
            System.out.println("Resposta C2B da API:\n" + c2bResponse);


            // =================================================================
            // EXEMPLO 2: Enviar Dinheiro para um Cliente (B2C Payment)
            // =================================================================
            LOGGER.info("\n-------------------------------------------------");
            LOGGER.info("Criando requisição de desembolso B2C...");
            
            B2CRequest b2cRequest = new B2CRequest(
                "500.00",                        // value: Valor a transferir para o cliente
                "847654321",                     // clientNumber: Conta M-Pesa de destino
                "171717",                        // agentId: Seu shortcode corporativo autorizado para B2C
                Cryptor.getId(),                 // transactionReference: ID único gerado para esta transferência
                "SALARY-009"                     // thirdPartyReference: Referência identificadora do lote/propósito
            );

            LOGGER.info("Enviando pedido B2C para a API...");
            String b2cResponse = mpesa.b2c(b2cRequest);
            System.out.println("Resposta B2C da API:\n" + b2cResponse);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ocorreu um erro ao processar as transações do M-Pesa", e);
        }
    }
}
