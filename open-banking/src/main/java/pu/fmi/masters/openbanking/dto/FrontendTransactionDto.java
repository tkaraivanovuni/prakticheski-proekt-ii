package pu.fmi.masters.openbanking.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * This class defines a data transfer object used for serializing transaction lists.
 */
@Getter
@Setter
public class FrontendTransactionDto {
	
	private String name;
	private String amount;
	private String currency;
	private String valueDate;
	
	public FrontendTransactionDto(TransactionListDto.TransactionTo transactionTo) {
		this.name = transactionTo.getName();
		this.amount = transactionTo.getTransactionAmount().getAmount();
		this.currency = transactionTo.getTransactionAmount().getCurrency();
		this.valueDate = transactionTo.getValueDate();
	}

}
