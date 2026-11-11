package io.github.kriolos.ewallet.mpesa;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class Transaction {

    private static final Logger LOGGER = Logger.getLogger(Transaction.class.getName());
    private final Request request;

    public Transaction() {
        this.request = new Request();
    }

    private String getBaseUrl() {
        boolean isProduction = "production".equalsIgnoreCase(Mpesa.getConfig().environment());
        return isProduction ? "api.vm.co.mz" : "api.sandbox.vm.co.mz";
    }

    public String c2b(C2BRequest data) throws Exception {
        String url = "https://" + getBaseUrl() + ":18352/ipg/v1x/c2bPayment/singleStage/";
        
        String jsonParams = """
            {
              "input_Amount": "%s",
              "input_CustomerMSISDN": "%s",
              "input_ServiceProviderCode": "%s",
              "input_TransactionReference": "%s",
              "input_ThirdPartyReference": "%s"
            }
            """.formatted(
                data.value(), 
                data.clientNumber(), 
                data.agentId(), 
                data.transactionReference(), 
                data.thirdPartyReference()
            );

        return request.post(url, jsonParams);
    }

    // Now uses the dedicated B2CRequest DTO
    public String b2c(B2CRequest data) throws Exception {
        String url = "https://" + getBaseUrl() + ":18345/ipg/v1x/b2cPayment/";
        
        String jsonParams = """
            {
              "input_Amount": "%s",
              "input_CustomerMSISDN": "%s",
              "input_ServiceProviderCode": "%s",
              "input_TransactionReference": "%s",
              "input_ThirdPartyReference": "%s"
            }
            """.formatted(
                data.value(), 
                data.clientNumber(), 
                data.agentId(), 
                data.transactionReference(), 
                data.thirdPartyReference()
            );

        return request.post(url, jsonParams);
    }

    public String b2b(B2BRequest data) throws Exception {
        String url = "https://" + getBaseUrl() + ":18349/ipg/v1x/b2bPayment/";
        
        String jsonParams = """
            {
              "input_TransactionReference": "%s",
              "input_PrimaryPartyCode": "%s",
              "input_ReceiverPartyCode": "%s",
              "input_Amount": "%s",
              "input_ThirdPartyReference": "%s"
            }
            """.formatted(
                data.transactionReference(), 
                data.agentId(), 
                data.agentReceiverId(), 
                data.value(), 
                data.thirdPartyReference()
            );

        return request.post(url, jsonParams);
    }

    public String reversal(ReversalRequest data) throws Exception {
        String url = "https://" + getBaseUrl() + ":18354/ipg/v1x/reversal/";
        
        String jsonParams = """
            {
              "input_TransactionID": "%s",
              "input_SecurityCredential": "%s",
              "input_InitiatorIdentifier": "%s",
              "input_ThirdPartyReference": "%s",
              "input_ServiceProviderCode": "%s",
              "input_ReversalAmount": "%s"
            }
            """.formatted(
                data.transactionId(), 
                data.securityCredential(), 
                data.indicatorIdentifier(), 
                data.thirdPartyReference(), 
                data.agentId(), 
                data.value()
            );

        return request.put(url, jsonParams);
    }

    public String status(StatusRequest data) throws Exception {
        String url = "https://" + getBaseUrl() + ":18353/ipg/v1x/queryTransactionStatus/";
        
        Map<String, String> params = new HashMap<>();
        params.put("input_QueryReference", data.transactionId());
        params.put("input_ThirdPartyReference", data.thirdPartyReference());
        params.put("input_ServiceProviderCode", data.agentId());
        
        return request.get(url, params);
    }

    public String customerName(CustomerNameRequest data) throws Exception {
        String url = "https://" + getBaseUrl() + ":19323/ipg/v1x/queryCustomerName/";
        
        Map<String, String> params = new HashMap<>();
        params.put("input_CustomerMSISDN", data.clientNumber());
        params.put("input_ThirdPartyReference", data.thirdPartyReference());
        params.put("input_ServiceProviderCode", data.agentId());
        
        return request.get(url, params);
    }
}
