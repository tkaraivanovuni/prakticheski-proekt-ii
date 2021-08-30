package pu.fmi.masters.openbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pu.fmi.masters.openbanking.model.User;

/**
 * This interface provides methods to retrieve data from user table.
 */
@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

	/**
	 * Retrieves a user by the id.
	 * 
	 * @param id - id to search by.
	 * @return - User with matching id.
	 */
	Optional<User> findById(int id);

	/**
	 * This method retrieves a {@link User} by its username and password.
	 * 
	 * @param username - username to match.
	 * @param password - password to match.
	 * @return - {@link User} with matching username and password.
	 */
	Optional<User> findUserByUsernameAndPassword(String username, String password);

	/**
	 * This method retrieve a {@link User} by its username.
	 * 
	 * @param username - username to match.
	 * @return - {@link User} with matching username.
	 */
	Optional<User> findUserByUsername(String username);

}
