package pu.fmi.masters.openbanking.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pu.fmi.masters.openbanking.model.Bank;
import pu.fmi.masters.openbanking.repository.BankRepo;

/**
 * This class is responsible for interacting with the {@link BankRepo}.
 */
@Service
public class BankInfoService {

	private BankRepo bankRepo;

	/**
	 * Constructor.
	 * 
	 * @param bankRepo - {@link BankRepo} object for interaction with the
	 *                 repository.
	 */
	public BankInfoService(BankRepo bankRepo) {
		this.bankRepo = bankRepo;
	}

	/**
	 * This method add a {@link Bank} to the repository.
	 * 
	 * @param bank - bank to be added.
	 * 
	 * @return - the added object.
	 */
	public Bank addBank(Bank bank) {
		return bankRepo.saveAndFlush(bank);
	}

	/**
	 * This method returns a list with all banks in the repository.
	 * 
	 * @return - a list object containing all banks.
	 */
	public List<Bank> getAllBanks() {
		return bankRepo.findAll();
	}

	/**
	 * This method returns a list of all {@link Bank} objects that contain the given
	 * string in their name.
	 * 
	 * @param bankName - string representing searched name.
	 * @return - list of matching results.
	 */
	public List<Bank> filterBanksByName(String bankName) {
		return bankRepo.findByBankNameContaining(bankName);
	}

}
