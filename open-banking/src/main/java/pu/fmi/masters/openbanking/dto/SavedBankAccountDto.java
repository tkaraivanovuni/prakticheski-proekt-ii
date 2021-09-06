package pu.fmi.masters.openbanking.dto;

import lombok.Data;
import pu.fmi.masters.openbanking.model.BankAccount;

@Data
public class SavedBankAccountDto {
	
	private final BankAccount bankAccount;
	private final String bankName;

}
