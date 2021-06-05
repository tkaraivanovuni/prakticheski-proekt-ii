package pu.fmi.masters.openbanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pu.fmi.masters.openbanking.model.BankAccount;
import pu.fmi.masters.openbanking.model.User;

/**
 * This interface provides methods to retrieve data from the bank_account table.
 */
@Repository
public interface BankAccountRepo extends JpaRepository<BankAccount, Integer> {
	
	/**
	 * This method retrieves all bank accounts.
	 */
	List<BankAccount> findAll();
	
	/**
	 * This method retrieves bank accounts belonging to the user.
	 * 
	 * @param user - {@link User} to search by.
	 * @return - a list of all bank accounts of the user.
	 */
	List<BankAccount> findByUser(User user);

	/**
	 * This method retrieves a bank account by its id.
	 * 
	 * @param id - the id of the bank account.
	 */
	Optional<BankAccount> findById(Integer id);
	
	/**
	 * This method retrieves an account by its iban.
	 * 
	 * @param iban - searched iban.
	 * @return - respective bank account.
	 */
	BankAccount findByAccountNumber(String iban);

}
