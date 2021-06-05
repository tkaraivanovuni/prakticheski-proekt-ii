package pu.fmi.masters.openbanking.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.service.BankInfoService;

/**
 * This class handles viewing and adding {@link Bank} objects.
 */
@RestController
public class BankController {

	private BankInfoService bankInfoService;

	@Autowired
	public BankController(BankInfoService bankInfoService) {
		;
		this.bankInfoService = bankInfoService;
	}

	/**
	 * This class handles adding {@link Bank} objects.
	 * 
	 * @param bankName  - name of the bank.
	 * @param apiKey    - application key.
	 * @param secret    - application secret.
	 * @param apiLink   - bank API url.
	 * @param authLink  - bank authentication url.
	 * @param tokenLink - bank token url.
	 * @param session   - current session.
	 * @return - string representing the outcome.
	 */
	@PostMapping(path = "/bank")
	@Secured(value = { "ROLE_ADMIN" })
	public String addTransaction(@RequestParam(value = "bank_name") String bankName,
			@RequestParam(value = "api_key") String apiKey, @RequestParam(value = "secret") String secret,
			@RequestParam(value = "api_link") String apiLink, @RequestParam(value = "auth_link") String authLink,
			@RequestParam(value = "token_link") String tokenLink, HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {

			Bank bank = new Bank();
			bank.setBankName(bankName);
			bank.setApiKey(apiKey);
			bank.setSecret(secret);
			bank.setApiLink(apiLink);
			bank.setAuthLink(authLink);
			bank.setTokenLink(tokenLink);

			bank = bankInfoService.addBank(bank);

			if (bank != null) {
				return String.valueOf(bank.getId());
			} else {
				return "Error: adding bank failed";
			}
		} else {
			return "Error: not logged in";
		}

	}

	/**
	 * This method handles viewing {@link Bank} objects.
	 * 
	 * @return - list of all banks.
	 */
	@GetMapping(path = "/bank")
	@Secured(value = { "ROLE_ADMIN" })
	public List<Bank> getAllTransactions() {
		return bankInfoService.getAllBanks();
	}

	/**
	 * This method handles searching for Banks.
	 * 
	 * @param bankName - searched name.
	 * @return - List of matching results.
	 */
	@GetMapping(path = "/bank/filter")
	@Secured(value = { "ROLE_USER" })
	public List<Bank> filterBanks(@RequestParam(value = "bank_name") String bankName) {
		return bankInfoService.filterBanksByName(bankName);
	}

}
