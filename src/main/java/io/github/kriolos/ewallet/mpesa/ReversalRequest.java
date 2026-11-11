package io.github.kriolos.ewallet.mpesa;

import java.util.Map;

public record ReversalRequest(
    String transactionId,
    String securityCredential,
    String indicatorIdentifier,
    String thirdPartyReference,
    String agentId,
    String value
) {
    public Map<String, String> toMap() {
        return Map.of(
            "transaction_id", transactionId,
            "security_credential", securityCredential,
            "indicator_identifier", indicatorIdentifier,
            "third_party_reference", thirdPartyReference,
            "agent_id", agentId,
            "value", value
        );
    }
}
