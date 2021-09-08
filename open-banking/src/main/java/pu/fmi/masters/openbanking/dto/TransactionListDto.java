package pu.fmi.masters.openbanking.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * This class defines a data transfer object used for deserializing transaction lists.
 */
@Data
public class TransactionListDto {
	
	private TransactionsAccountTo account;
	private TransactionsListTo transactions;
	
	@Data
	private static class TransactionsAccountTo {
		private String iban;
	}
	
	@Data
	public static class TransactionsListTo {
		private List<TransactionTo> booked;
		private List<TransactionTo> pending;
		private LinksTo _links;
	}
	
	@Data
	public static class TransactionTo {
		@JsonProperty("creditorName")
		@JsonAlias("debtorName")
		private String name;
		@JsonProperty("creditorAccount")
		@JsonAlias("debtorAccount")
		private TransactionsAccountTo account;
		private AmountTo transactionAmount;
		private String bookingDate;
		private String valueDate;
		private String remittanceInformationUnstructured;
	}
	
	@Data
	static class AmountTo {
		private String currency;
		private String amount;
	}
	
	@Data
	private static class LinksTo {
		private AccountLinkTo account;
	}
	
	@Data
	private static class AccountLinkTo {
		private String href;
	}

}
