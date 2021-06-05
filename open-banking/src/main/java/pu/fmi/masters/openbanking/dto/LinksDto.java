package pu.fmi.masters.openbanking.dto;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
public class LinksDto {
	
	private BalancesDto balances;
	private TransactionsDto transactions;
	
	/**
	 * No-arguments constructor.
	 */
	public LinksDto() {}

	public BalancesDto getBalances() {
		return balances;
	}

	public void setBalances(BalancesDto balances) {
		this.balances = balances;
	}

	public TransactionsDto getTransactions() {
		return transactions;
	}

	public void setTransactions(TransactionsDto transactions) {
		this.transactions = transactions;
	}

}
