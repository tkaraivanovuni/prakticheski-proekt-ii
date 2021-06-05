package pu.fmi.masters.openbanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pu.fmi.masters.openbanking.model.Bank;

/**
 * This interface provides methods to retrieve data from the bank table.
 */
@Repository
public interface BankRepo extends JpaRepository<Bank, Integer> {

	/**
	 * This method returns a list of banks with matching name.
	 * 
	 * @param bankName - string representing searched name.
	 * @return - list with results.
	 */
	List<Bank> findByBankNameContaining(String bankName);

	/**
	 * This method retrieves a {@link Bank} by its id.
	 * 
	 * @param id - id to search by.
	 * @return - matching {@link Bank}.
	 */
	Optional<Bank> findById(Integer id);
	
	/**
	 * This method retrieves a {@link Bank} by its name.
	 * 
	 * @param bankName - name to search by.
	 * @return - matching {@link Bank}.
	 */
	Optional<Bank> findByBankName(String bankName);

}
