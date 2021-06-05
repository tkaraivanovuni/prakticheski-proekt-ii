package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data
 * representing balance amount.
 */
@JsonDeserialize
public class BalanceAmountDto {
	
	private String currency;
	private double amount;
	
	/**
	 * No-args constructor.
	 */
	public BalanceAmountDto() {}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
