package pu.fmi.masters.openbanking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.minidev.json.JSONObject;
import pu.fmi.masters.openbanking.dto.PaymentResponseDto;
import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.repository.BankRepo;

/**
 * This class handled operations related to payment requests.
 */
@Service
public class PaymentHandlingService {

	@Autowired
	private BankRepo bankRepo;
	@Autowired
	private RestTemplate restTemplate;

	public ResponseEntity<PaymentResponseDto> initiatePayment(String token, int bankId, String requestId,
			String ipAddress, String iban, String amount, String currency, String creditorIban, String creditorName) {
		Optional<Bank> optionalBank = bankRepo.findById(bankId);
		if (!optionalBank.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		String paymentsEndpoint = "payments/";
		if (currency.equals("BGN")) {
			paymentsEndpoint = paymentsEndpoint.concat("domestic-credit-transfers-bgn");			
		} else {
			paymentsEndpoint = paymentsEndpoint.concat("sepa-credit-transfers");
		}
		String url = optionalBank.get().getApiLink() + paymentsEndpoint;
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.add("x-ibm-client-id", "083ec7a8-584a-4e61-a7b0-17f8e44bfb38");
		httpHeaders.add("authorization", "Bearer " + token);
		httpHeaders.add("x-request-id", requestId);
		httpHeaders.add("psu-ip-address", ipAddress);
		httpHeaders.add("tpp-redirect-uri", "https://localhost:8443/callback");
		JSONObject paymentBody = createBody(iban, amount, currency, creditorIban, creditorName);
		HttpEntity<String> entity = new HttpEntity<String>(paymentBody.toString(), httpHeaders);
		return restTemplate.exchange(url, HttpMethod.POST, entity, PaymentResponseDto.class);
	}

	private JSONObject createBody(String iban, String amount, String currency, String creditorIban,
			String creditorName) {
		JSONObject account = new JSONObject();
		account.put("iban", iban);
		JSONObject instructedAmount = new JSONObject();
		instructedAmount.put("currency", currency);
		instructedAmount.put("amount", amount);
		JSONObject creditorAccount = new JSONObject();
		creditorAccount.put("iban", creditorIban);
		JSONObject paymentBody = new JSONObject();
		paymentBody.put("debtorAccount", account);
		paymentBody.put("instructedAmount", instructedAmount);
		paymentBody.put("creditorAccount", creditorAccount);
		paymentBody.put("creditorName", creditorName);
		paymentBody.put("serviceLevel", "SEPA");
		if (currency.equals("BGN")) {
			paymentBody.put("remittanceInformationUnstructured", "Ref Number Merchant");
		}
		return paymentBody;
	}

}
