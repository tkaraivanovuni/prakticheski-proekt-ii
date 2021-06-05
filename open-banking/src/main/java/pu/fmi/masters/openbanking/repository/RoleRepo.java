package pu.fmi.masters.openbanking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pu.fmi.masters.openbanking.model.Role;

/**
 * This interface provides methods to retrieve data from role table.
 */
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {
	
	/**
	 * This method retrieves all defined roles.
	 */
	List<Role> findAll();

	/**
	 * This method retrieves a {@link Role} by it's id.
	 * 
	 * @param id - searched id;
	 * @return - {@link Role} with matching id.
	 */
	Optional<Role> findById(Integer id);

	/**
	 * This method retrieves a {@link Role} by it's name.
	 * 
	 * @param role - searched role.
	 * @return - {@link Role} with matching role name.
	 */
	Optional<Role> findByRole(String role);

}
