package pu.fmi.masters.openbanking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.EqualsAndHashCode;

/**
 * This class provides the main characteristics of the {@link BankAccount}
 * data model.
 */
@Entity
@Table(name = "bank_accounts")
@EqualsAndHashCode
public class BankAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonManagedReference
	private User user;
	
	@ManyToOne
	@JoinColumn(name="bank_id")
	private Bank bank;

	@Column(name = "account_number", unique = true, nullable = false)
	private String accountNumber;

	@Column(nullable = false)
	private String currency;
	
	@Column(nullable = false)
	private String product;

	/**
	 * No-arguments constructor.
	 */
	public BankAccount() {
	}

	/**
	 * Required-arguments constructor.
	 * 
	 * @param user          - {@link User} holding the account.
	 * @param bank          - {@link Bank} the account is at.
	 * @param accountNumber - the account's number
	 * @param currency      - the currency of the account.
	 */
	public BankAccount(User user, Bank bank, String accountNumber, String currency) {
		this.user = user;
		this.bank = bank;
		this.accountNumber = accountNumber;
		this.currency = currency;
	}

	/**
	 * This method returns the account's id.
	 * 
	 * @return - int representing the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method sets the account's id.
	 * 
	 * @param id - new id to be set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method returns the user holding the account.
	 * 
	 * @return - {@link User} representing the holder.
	 */
	public User getUser() {
		return user;
	}

	/**
	 * This method sets the user holding the account.
	 * 
	 * @param user - new {@link User} to be set.
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * This method returns the bank the account is at.
	 * 
	 * @return - {@link Bank} representing the bank.
	 */
	public Bank getBank() {
		return bank;
	}

	/**
	 * This method sets the bank that the account is in.
	 * 
	 * @param bank - {@link Bank} to be set.
	 */
	public void setBank(Bank bank) {
		this.bank = bank;
	}

	/**
	 * This method returns the account number.
	 * 
	 * @return - string representing account number.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * This method sets the account number.
	 * 
	 * @param accountNumber - new account number to be set.
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * This method returns currency.
	 * 
	 * @return - string representing account currency.
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * This method sets the account currency.
	 * 
	 * @param currency - currency to be set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	/**
	 * This method returns the account product.
	 * 
	 * @return - string representing account product.
	 */
	public String getProduct() {
		return product;
	}
	
	/**
	 * This method sets the account product.
	 * 
	 * @param currency - product to be set.
	 */
	public void setProduct(String product) {
		this.product = product;
	}

}
