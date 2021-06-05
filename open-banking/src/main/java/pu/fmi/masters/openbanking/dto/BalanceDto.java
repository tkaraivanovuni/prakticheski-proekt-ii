package pu.fmi.masters.openbanking.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * This class defines a data transfer object used for deserializing JSON data
 * representing balance.
 */
@JsonDeserialize
public class BalanceDto {
	
	private String balanceType;
	private BalanceAmountDto balanceAmount;
	
	/**
	 * No-args constructor.
	 */
	public BalanceDto() {}

	public String getBalanceType() {
		return balanceType;
	}

	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}

	public BalanceAmountDto getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BalanceAmountDto balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

}
