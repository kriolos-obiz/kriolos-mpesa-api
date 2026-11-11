package io.github.kriolos.ewallet.mpesa;

import java.util.Map;

public record CustomerNameRequest(
    String clientNumber,
    String thirdPartyReference,
    String agentId
) {
    public Map<String, String> toMap() {
        return Map.of(
            "client_number", clientNumber,
            "third_party_reference", thirdPartyReference,
            "agent_id", agentId
        );
    }
}
