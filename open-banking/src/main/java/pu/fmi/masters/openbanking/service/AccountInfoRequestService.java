package pu.fmi.masters.openbanking.service;

import java.io.IOException;
import java.util.Optional;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pu.fmi.masters.openbanking.dto.AccountBalanceDto;
import pu.fmi.masters.openbanking.dto.AccountsListDto;
import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.repository.BankRepo;

/**
 * This class handles requests for account information,
 */
@Service
public class AccountInfoRequestService {

	private BankRepo bankRepo;
	private RestTemplate restTemplate;

	/**
	 * Constructor.
	 * 
	 * @param bankRepo     - instance of {@link BankRepo} for accessing data.
	 * @param restTemplate - instance of {@link RestTemplate} for making external
	 *                     API calls.
	 */
	@Autowired
	public AccountInfoRequestService(BankRepo bankRepo, RestTemplate restTemplate) {
		this.bankRepo = bankRepo;
		this.restTemplate = restTemplate;
	}

	/**
	 * This method sends a request for the list of accessible accounts to the
	 * {@link Bank} resource server and returns the response.
	 * 
	 * @param token - string representing authorization token.
	 * @param bankId - id of the bank.
	 * @param requestId - id of the request.
	 * @return - AccountListDto representing the response.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ResponseEntity<AccountsListDto> requestAccountsList(String token, int bankId, String requestId)
			throws ClientProtocolException, IOException {
		Optional<Bank> optionalBank = bankRepo.findById(bankId);
		if (!optionalBank.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		String url = optionalBank.get().getApiLink() + "accounts";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("x-ibm-client-id", "083ec7a8-584a-4e61-a7b0-17f8e44bfb38");
		httpHeaders.add("authorization", "Bearer " + token);
		httpHeaders.add("x-request-id", requestId);
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		return restTemplate.exchange(url, HttpMethod.GET, entity, AccountsListDto.class);
	}
	
	/**
	 * This method sends a request for the list of accessible accounts to the
	 * {@link Bank} resource server and returns the response.
	 * 
	 * @param token - string representing authorization token.
	 * @param bankId - id of the bank.
	 * @param requestId - id of the request.
	 * @return - AccountListDto representing the response.
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public ResponseEntity<AccountBalanceDto> requestAccountBalance(String token, int bankId, String requestId, String iban)
			throws ClientProtocolException, IOException {
		Optional<Bank> optionalBank = bankRepo.findById(bankId);
		if (!optionalBank.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		String url = optionalBank.get().getApiLink() + "accounts/" + iban + "/balances";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("x-ibm-client-id", "083ec7a8-584a-4e61-a7b0-17f8e44bfb38");
		httpHeaders.add("authorization", "Bearer " + token);
		httpHeaders.add("x-request-id", requestId);
		HttpEntity<String> entity = new HttpEntity<String>(httpHeaders);
		return restTemplate.exchange(url, HttpMethod.GET, entity, AccountBalanceDto.class);
	}
	
	

}
