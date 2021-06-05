package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
@JsonDeserialize
public class AccountsListDto {
	
	private BankAccountDto[] accounts;
	
	/**
	 * No-arguments constructor.
	 */
	public AccountsListDto() {}

	public BankAccountDto[] getAccounts() {
		return accounts;
	}

	public void setAccounts(BankAccountDto[] accounts) {
		this.accounts = accounts;
	}

}
