package pu.fmi.masters.openbanking.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.openqa.selenium.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import pu.fmi.masters.openbanking.dto.AccountBalanceDto;
import pu.fmi.masters.openbanking.dto.AccountsListDto;
import pu.fmi.masters.openbanking.dto.SavedBankAccountDto;
import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.model.BankAccount;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.BankAccountRepo;
import pu.fmi.masters.openbanking.repository.BankRepo;
import pu.fmi.masters.openbanking.service.AccountInfoRequestService;
import pu.fmi.masters.openbanking.service.OauthRequestService;
import pu.fmi.masters.openbanking.service.UserAccountService;

/**
 * This class handles viewing and adding {@link BankAccount} objects.
 */
@RestController
public class BankAccountController {

	private AccountInfoRequestService accountInfoRequestService;
	private OauthRequestService oauthRequestService;
	private BankAccountRepo bankAccountRepo;
	private BankRepo bankRepo;
	private UserAccountService userAccountService;

	/**
	 * Constructor.
	 * 
	 * @param bankAccountRepo - {@link BankAccountRepo} for data management.
	 * @param bankRepo        - {@link BankRepo} for data management.
	 */
	@Autowired
	public BankAccountController(BankAccountRepo bankAccountRepo, BankRepo bankRepo,
			OauthRequestService oauthRequestService, AccountInfoRequestService accountInfoRequestService, UserAccountService userAccountService) {
		this.bankAccountRepo = bankAccountRepo;
		this.bankRepo = bankRepo;
		this.oauthRequestService = oauthRequestService;
		this.accountInfoRequestService = accountInfoRequestService;
		this.userAccountService = userAccountService;
	}

	/**
	 * This method handles adding new bank accounts.
	 * 
	 * @param bankId        - id of the bank of the account.
	 * @param accountNumber - account number.
	 * @param currency      - currency of the account.
	 * @param session       - the current session.
	 * @return - string representing the outcome.
	 */
	@PostMapping(path = "/bank-account")
	public String addBankAccount(@RequestParam(value = "bank_id") int bankId,
			@RequestParam(value = "account_number") String accountNumber,
			@RequestParam(value = "currency") String currency, @RequestParam(value = "product") String product,
			HttpSession session) {
		int userId = (Integer) session.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);
		Optional<Bank> optionalBank = bankRepo.findById(bankId);
		if (!optionalBank.isPresent()) {
			throw new InvalidArgumentException("Bank not found!");
		}
		Bank bank = optionalBank.get();
		if (user != null && bank != null) {

			BankAccount bankAccount = new BankAccount();
			bankAccount.setUser(user);
			bankAccount.setBank(bank);
			bankAccount.setAccountNumber(accountNumber);
			bankAccount.setCurrency(currency);
			bankAccount.setProduct(product);

			bankAccount = bankAccountRepo.saveAndFlush(bankAccount);

			if (bankAccount != null) {
				return String.valueOf(bankAccount.getId());
			} else {
				return "Error: adding account failed";
			}
		} else {
			return "Error: not logged in";
		}

	}

	/**
	 * This method handles viewing bank accounts.
	 * 
	 * @param session - current session.
	 * @return - list of {@link BankAccount}.
	 */
	@GetMapping(path = "/bank-account")
	public Set<SavedBankAccountDto> getBankAccounts(HttpSession session) {
		int userId = (Integer) session.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);
		Set<SavedBankAccountDto> bankAccounts = new HashSet<>();
		for (BankAccount bankAccount : user.getAccounts()) {
			SavedBankAccountDto bankAccountDto = new SavedBankAccountDto(bankAccount, bankAccount.getBank().getBankName());
			bankAccounts.add(bankAccountDto);
		}
		return bankAccounts;
	}
	
	@DeleteMapping(path = "/bank-account/{iban}")
	@Transactional
	public ResponseEntity<String> deleteAccount(@PathVariable(value = "iban") String iban, HttpSession session) {
		BankAccount bankAccount = bankAccountRepo.findByAccountNumber(iban);
		if (bankAccount != null) {
			User user = bankAccount.getUser();
			if (user.deleteBankAccount(bankAccount) == false) {
				throw new IllegalArgumentException("User could not be updated");
			};
			return new ResponseEntity<>("Deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("Wrong iban", HttpStatus.BAD_REQUEST);
	}

	/**
	 * This method retrieves list of accounts.
	 * 
	 * @param bankId  - bank id.
	 * @param scope   - requested scope.
	 * @param state   - state attribute.
	 * @param session - current session.
	 * @return - response containing the outcome.
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	@GetMapping(path = "/account")
	public ResponseEntity<Object> readBankAccount(@RequestParam(value = "bank_id") int bankId,
			@RequestParam(value = "scope") String scope, @RequestParam(value = "state") String state,
			HttpSession session) throws UnrecoverableKeyException, KeyManagementException, KeyStoreException,
			NoSuchAlgorithmException, CertificateException {
		session.setAttribute("bank_id", bankId);
		if (session.getAttribute("account_token") == null) {
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(bankId, scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		}
		String requestId = UUID.randomUUID().toString();
		ResponseEntity<AccountsListDto> response = null;
		try {
			response = accountInfoRequestService.requestAccountsList((String) session.getAttribute("account_token"), bankId,
					requestId);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (HttpClientErrorException e) {
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(bankId, scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		}
		if (response == null) {
			return new ResponseEntity<>("Request could not be completed as is!", HttpStatus.GATEWAY_TIMEOUT);
		}
		if (response.getStatusCodeValue() == 200) {
			return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
		} else if (response.getStatusCodeValue() == 400) {
			return new ResponseEntity<>("Request could not be completed as is!", HttpStatus.BAD_REQUEST);
		} else if (response.getStatusCodeValue() == 401) {
			System.out.println("unauthorized");
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(bankId, scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		} else if (response.getStatusCodeValue() == 403) {
			return new ResponseEntity<>("More privileges required to complete this action!", HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<>("Request could not be handled by your bank!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * This method retrieves the account's balance.
	 * 
	 * @param bankId  - bank id.
	 * @param scope   - requested scope.
	 * @param state   - state attribute.
	 * @param iban    - iban of the account.
	 * @param session - current session.
	 * @return - response with the outcome.
	 * @throws UnrecoverableKeyException
	 * @throws KeyManagementException
	 * @throws KeyStoreException
	 * @throws NoSuchAlgorithmException
	 * @throws CertificateException
	 */
	@GetMapping(path = "/balance")
	public ResponseEntity<Object> readAccountBalance(@RequestParam(value = "bank_id") int bankId,
			@RequestParam(value = "scope") String scope, @RequestParam(value = "state") String state,
			@RequestParam(value = "iban") String iban, HttpSession session) throws UnrecoverableKeyException,
			KeyManagementException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
		session.setAttribute("bank_id", bankId);
		if (session.getAttribute("account_token") == null) {
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(bankId, scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		}
		String requestId = UUID.randomUUID().toString();
		ResponseEntity<AccountBalanceDto> response = null;
		try {
			response = accountInfoRequestService.requestAccountBalance((String) session.getAttribute("account_token"), bankId,
					requestId, iban);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (response == null) {
			return new ResponseEntity<>("Request timed out!", HttpStatus.GATEWAY_TIMEOUT);
		}
		if (response.getStatusCodeValue() == 200) {
			return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
		} else if (response.getStatusCodeValue() == 400) {
			return new ResponseEntity<>("Request could not be completed as is!", HttpStatus.BAD_REQUEST);
		} else if (response.getStatusCodeValue() == 401) {
			return new ResponseEntity<>(oauthRequestService.createOauthCodeRequest(bankId, scope, state),
					HttpStatus.TEMPORARY_REDIRECT);
		} else if (response.getStatusCodeValue() == 403) {
			return new ResponseEntity<>("More privileges required to complete this action!", HttpStatus.FORBIDDEN);
		} else {
			return new ResponseEntity<>("Request could not be handled by your bank!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
