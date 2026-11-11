package io.github.kriolos.ewallet.mpesa;

import java.util.Map;

public record B2BRequest(
    String transactionReference,
    String agentId,
    String agentReceiverId,
    String value,
    String thirdPartyReference
) {
    public Map<String, String> toMap() {
        return Map.of(
            "transaction_reference", transactionReference,
            "agent_id", agentId,
            "agent_receiver_id", agentReceiverId,
            "value", value,
            "third_party_reference", thirdPartyReference
        );
    }
}
