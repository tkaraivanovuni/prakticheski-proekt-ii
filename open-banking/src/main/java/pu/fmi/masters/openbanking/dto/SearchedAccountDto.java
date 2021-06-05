package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data
 * representing bank account iban.
 */
@JsonDeserialize
public class SearchedAccountDto {
	
	private String iban;
	
	/**
	 * No-args constructor.
	 */
	public SearchedAccountDto() {}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

}
