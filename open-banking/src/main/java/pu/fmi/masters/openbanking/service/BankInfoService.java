package pu.fmi.masters.openbanking.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	public Bank addNewBank(Bank bank) {
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
	
	/**
	 * This method deletes a {@link Bank} by id.
	 * 
	 * @param id -id of the bank to be deleted
	 */
	public void deleteBank(int id) {
		bankRepo.deleteById(id);
	}
	
	/**
	 * This method updates a {@link Bank}.
	 * 
	 * @param bank - new bank info.
	 * @return - the updated bank.
	 */
	public Bank editBank(Bank bank) {
		return bankRepo.saveAndFlush(bank);
	}
	
	/**
	 * Retrieves a bank's name by its id.
	 * 
	 * @param id - id to search by.
	 * @return - matching bank's name.
	 */
	public String retrieveBankName(String id) {
		Optional<Bank> optionalBank = bankRepo.findById(Integer.parseInt(id));
		if (!optionalBank.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No bank found with this id!");
		}
		return optionalBank.get().getBankName();
	}

}
