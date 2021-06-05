package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data
 * representing bank account balance.
 */
@JsonDeserialize
public class AccountBalanceDto {
	
	private SearchedAccountDto account;
	private BalanceDto[] balances;

	/**
	 * No-args constructor.
	 */
	public AccountBalanceDto() {}

	public SearchedAccountDto getAccount() {
		return account;
	}

	public void setAccount(SearchedAccountDto account) {
		this.account = account;
	}
	
	public BalanceDto[] getBalances() {
		return balances;
	}

	public void setBalances(BalanceDto[] balances) {
		this.balances = balances;
	}

}
