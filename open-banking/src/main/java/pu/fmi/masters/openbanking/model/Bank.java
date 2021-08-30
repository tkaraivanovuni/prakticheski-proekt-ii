package pu.fmi.masters.openbanking.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * This class provides the main characteristics of the {@link Bank} data
 * model.
 */
@Entity
@Table(name = "banks")
public class Bank {

	@Id
	@Column(name = "bank_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "bank_name", unique = true, nullable = false)
	private String bankName;

	@Column(name = "api_key", nullable = false)
	private String apiKey;

	@Column(name = "secret", nullable = false)
	private String secret;

	@Column(name = "api_link", nullable = false)
	private String apiLink;

	@Column(name = "auth_link", nullable = false)
	private String authLink;

	@Column(name = "token_link", nullable = false)
	private String tokenLink;
	
	@OneToMany(mappedBy = "bank", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<BankAccount> savedAccounts;

	/**
	 * No arguments constructor.
	 */
	public Bank() {
	}

	/**
	 * Required-arguments constructor.
	 * 
	 * @param bankName  - string representing the bank's name.
	 * @param apiKey    - string representing the app's API key.
	 * @param secret    - string representing the app's secret.
	 * @param apiLink   - string representing the bank's API url.
	 * @param authLink  - string representing the bank's authorization server url.
	 * @param tokenLink - string representing the bank's token request url.
	 */
	public Bank(String bankName, String apiKey, String secret, String apiLink, String authLink, String tokenLink) {
		this.bankName = bankName;
		this.apiKey = apiKey;
		this.secret = secret;
		this.apiLink = apiLink;
		this.authLink = authLink;
		this.tokenLink = tokenLink;
	}

	/**
	 * This method return's the bank's id/
	 * 
	 * @return id - int representing the bank's id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method sets the bank's id.
	 * 
	 * @param id - int representing the new id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method returns the bank's name.
	 * 
	 * @return - string representing the bank's name.
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * This method sets the bank's name.
	 * 
	 * @param bankName - new name to be set.
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * This method returns the app's API key.
	 * 
	 * @return - string representing the app's API key.
	 */
	public String getApiKey() {
		return apiKey;
	}

	/**
	 * This method sets the app's API key.
	 * 
	 * @param apiKey - new API key to be set.
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	/**
	 * This method returns the app's secret.
	 * 
	 * @return - string representing the app's secret
	 */
	public String getSecret() {
		return secret;
	}

	/**
	 * This method sets the app's secret.
	 * 
	 * @param secret - new secret to be set.
	 */
	public void setSecret(String secret) {
		this.secret = secret;
	}

	/**
	 * This method returns the bank's API url.
	 * 
	 * @return - string representing the bank's API url.
	 */
	public String getApiLink() {
		return apiLink;
	}

	/**
	 * This method sets the bank's API url.
	 * 
	 * @param apiLink - new API url to be set.
	 */
	public void setApiLink(String apiLink) {
		this.apiLink = apiLink;
	}

	/**
	 * This method returns the bank's auth server url.
	 * 
	 * @return - string representing the bank's auth url.
	 */
	public String getAuthLink() {
		return authLink;
	}

	/**
	 * This method sets the bank's auth server url.
	 * 
	 * @param authLink - new auth url to be set.
	 */
	public void setAuthLink(String authLink) {
		this.authLink = authLink;
	}

	/**
	 * This method returns the bank's token request url.
	 * 
	 * @return - string representing the bank's token request url.
	 */
	public String getTokenLink() {
		return tokenLink;
	}

	/**
	 * This method sets the bank's token request url.
	 * 
	 * @param tokenLink - new token url to be set.
	 */
	public void setTokenLink(String tokenLink) {
		this.tokenLink = tokenLink;
	}

}
