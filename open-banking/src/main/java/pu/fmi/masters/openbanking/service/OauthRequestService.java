package pu.fmi.masters.openbanking.service;

import java.util.Optional;

import org.openqa.selenium.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pu.fmi.masters.openbanking.dto.TokenResponseDto;
import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.repository.BankRepo;

/**
 * This class is responsible for building requests to external API.
 */
@Service
public class OauthRequestService {
	
	private RestTemplate restTemplate = new RestTemplate();
	private BankRepo bankRepo;
	
	@Autowired
	public OauthRequestService(BankRepo bankRepo) {
		this.bankRepo = bankRepo;
	}

	/**
	 * This method is responsible for preparing oauth requests.
	 * 
	 * @param bankId  - bank that the request is for.
	 * @param scope - scope of the requested authorization.
	 * @param state - state parameter.
	 * @return - string representation of the request uri.
	 */
	public String createOauthCodeRequest(int bankId, String scope, String state) {
		Optional<Bank> bankOptional = bankRepo.findById(bankId);
		if (!bankOptional.isPresent()) {
			throw new InvalidArgumentException("Bank not found!");
		}
		Bank bank = bankOptional.get();
		StringBuilder requestBuilder = new StringBuilder(bank.getAuthLink());
		requestBuilder.append("?response_type=code");
		requestBuilder.append("&client_id=" + bank.getApiKey());
		requestBuilder.append("&redirect_uri=https://openbanking.com:8443/callback");
		requestBuilder.append("&scope=" + scope);
		requestBuilder.append("&state=" + state);
		return requestBuilder.toString();
	}
	
	/**
	 * This method exchanges an authorization code for an authorization token.
	 * @param bank
	 * @param code
	 * @return
	 */
	public String requestOauthToken(Bank bank, String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("grant_type", "authorization_code");
		bodyMap.add("code", code);
		bodyMap.add("redirect_uri", "https://openbanking.com:8443/callback");
		bodyMap.add("client_id", bank.getApiKey());
		bodyMap.add("client_secret", bank.getSecret());
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(bodyMap, headers);
		ResponseEntity<TokenResponseDto> response = restTemplate.postForEntity(bank.getTokenLink(), request, TokenResponseDto.class);
		return response.getBody().getAccess_token();
	}

}
