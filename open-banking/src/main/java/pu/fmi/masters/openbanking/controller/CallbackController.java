package pu.fmi.masters.openbanking.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.repository.BankRepo;
import pu.fmi.masters.openbanking.service.OauthRequestService;

/**
 * This class handles callback from authentication servers after a user has
 * authorized the application to use their data.
 */
@RestController
public class CallbackController {

	@Autowired
	private OauthRequestService oauthRequestService;
	@Autowired
	private BankRepo bankRepo;

	/**
	 * This method handles responses from authentication server to exchange code for
	 * authorization token.
	 * 
	 * @param code        - code to be exchanged.
	 * @param scope       - scope the code is valid for.
	 * @param state       - state parameter from original request.
	 * @param httpSession - current http session.
	 * 
	 * @return - string redirecting the client to the requested page.
	 */
	@GetMapping(path = "/callback")
	public ResponseEntity<String> getOauthCode(@RequestParam(value = "code") String code, @RequestParam(value = "scope") String scope,
			@RequestParam(value = "state") String state, HttpSession httpSession) {
		Optional<Bank> bankOptional = bankRepo.findById((Integer) httpSession.getAttribute("bank_id"));
		Bank bank = bankOptional.get();
		String token = oauthRequestService.requestOauthToken(bank, code);
		if (scope.contains("pis.sandbox")) {
			httpSession.setAttribute("payment_token", token);
		} else {
			httpSession.setAttribute("account_token", token);
		}
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.set("Location", state);
		return new ResponseEntity<>("Redirecting", responseHeaders, HttpStatus.TEMPORARY_REDIRECT);
	}

}
