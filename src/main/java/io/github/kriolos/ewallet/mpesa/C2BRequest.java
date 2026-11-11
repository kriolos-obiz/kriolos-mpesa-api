package io.github.kriolos.ewallet.mpesa;

import java.util.Map;

public record C2BRequest(
    String value,
    String clientNumber,
    String agentId,
    String transactionReference,
    String thirdPartyReference
) {
    public Map<String, String> toMap() {
        return Map.of(
            "value", value,
            "client_number", clientNumber,
            "agent_id", agentId,
            "transaction_reference", transactionReference,
            "third_party_reference", thirdPartyReference
        );
    }
}
