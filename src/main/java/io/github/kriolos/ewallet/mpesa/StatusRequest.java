package io.github.kriolos.ewallet.mpesa;

import java.util.Map;

public record StatusRequest(
    String transactionId,
    String thirdPartyReference,
    String agentId
) {
    public Map<String, String> toMap() {
        return Map.of(
            "transaction_id", transactionId,
            "third_party_reference", thirdPartyReference,
            "agent_id", agentId
        );
    }
}
