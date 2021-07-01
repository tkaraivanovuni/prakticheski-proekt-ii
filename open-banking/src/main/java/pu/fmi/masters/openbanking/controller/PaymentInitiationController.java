package pu.fmi.masters.openbanking.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pu.fmi.masters.openbanking.dto.PaymentResponseDto;
import pu.fmi.masters.openbanking.service.OauthRequestService;
import pu.fmi.masters.openbanking.service.PaymentHandlingService;

/**
 * This class is responsible for handling payment initiation requests.
 */
@RestController
public class PaymentInitiationController {

	@Autowired
	private PaymentHandlingService paymentService;
	@Autowired
	private OauthRequestService oauthRequestService;

	/**
	 * This method handles payment requests.
	 * 
	 * @param bankId       - id of the bank of the debtor.
	 * @param scope        - scope of the operation.
	 * @param state        - state of the request.
	 * @param debitorIban  - iban of the debtor.
	 * @param amount       - instructed amount.
	 * @param currency     - instructed currency.
	 * @param creditorIban - iban of the creditor.
	 * @param creditorName - name of the creditor.
	 * @param session      - current session.
	 * @param request      - current request.
	 * @return - response containing the outcome.
	 */
	@PostMapping(path = "payment")
	public ResponseEntity<Object> makePayment(@RequestParam(value = "bank_id") String bankId,
			@RequestParam(value = "scope") String scope, @RequestParam(value = "state") String state,
			@RequestParam(value = "debitor_iban") String debitorIban, @RequestParam(value = "amount") String amount,
			@RequestParam(value = "currency") String currency,
			@RequestParam(value = "creditor_iban") String creditorIban,
			@RequestParam(value = "creditor_name") String creditorName, HttpSession session,
			HttpServletRequest request) {
		String userIpAddress = (request.getRemoteAddr());
		if (session.getAttribute("payment_token") == null) {
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(Integer.parseInt(bankId), scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		}
		String requestId = UUID.randomUUID().toString();
		ResponseEntity<PaymentResponseDto> response = null;
		try {
			response = paymentService.initiatePayment((String) session.getAttribute("payment_token"), Integer.parseInt(bankId), requestId,
					"46.10.64.35", debitorIban, amount, currency, creditorIban, creditorName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (response == null) {
			return new ResponseEntity<>("Request could not be completed as is!", HttpStatus.GATEWAY_TIMEOUT);
		}
		if (response.getStatusCodeValue() == 201) {
			return new ResponseEntity<>(response.getBody(), HttpStatus.CREATED);
		} else if (response.getStatusCodeValue() == 400) {
			return new ResponseEntity<>("Request could not be completed as is!", HttpStatus.BAD_REQUEST);
		} else if (response.getStatusCodeValue() == 401) {
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(Integer.parseInt(bankId), scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		} else if (response.getStatusCodeValue() == 403) {
			return new ResponseEntity<>("More privileges required to complete this action!", HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
