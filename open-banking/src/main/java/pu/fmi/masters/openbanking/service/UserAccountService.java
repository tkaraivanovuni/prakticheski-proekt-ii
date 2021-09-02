package pu.fmi.masters.openbanking.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import lombok.Setter;
import pu.fmi.masters.openbanking.dto.UserDto;
import pu.fmi.masters.openbanking.model.Role;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;

/**
 * This class handles operations related to managing user accounts. Methods are
 * provided for adding, updating, retrieving and deleting user accounts.
 */
@Service
@ConfigurationProperties(prefix = "administration")
public class UserAccountService {

	@Autowired
	private UserRepo userRepo;

	@Setter
	private Map<Integer, Map<String, String>> admins = new HashMap<>();

	@PostConstruct
	private void initAdmin() {
		admins.forEach((key, value) -> userRepo.save(new User(key, value.get("username"), value.get("email"),
				hashPassword(value.get("password")), Role.ADMIN)));
		userRepo.flush();
	}

	/**
	 * Retrives a user by id.
	 * 
	 * @param id - int representing id.
	 * @return - a matching user.
	 */
	public User retrieveById(int id) {
		Optional<User> optionalUser = userRepo.findById(id);
		if (!optionalUser.isPresent()) {
			throw new IllegalArgumentException("Wrong user id!");
		}
		return optionalUser.get();
	}

	/**
	 * This method handles adding a new user.
	 * 
	 * @param userDto - {@link UserDto} representing user to be added.
	 * @return - the added user.
	 */
	public User addUser(UserDto userDto) {
		User user = new User(userDto.getUsername(), hashPassword(userDto.getPassword()), userDto.getEmail());
		user.setRole(Role.USER);
		return userRepo.saveAndFlush(user);
	}

	/**
	 * This method handles adding a new admin.
	 * 
	 * @param userDto - {@link UserDto} representing admin to be added.
	 * @return - the added user.
	 */
	public User addAdmin(UserDto userDto) {
		User user = new User(userDto.getUsername(), hashPassword(userDto.getPassword()), userDto.getEmail());
		user.setRole(Role.ADMIN);
		return userRepo.saveAndFlush(user);
	}

	/**
	 * Updates the user with the new values.
	 * 
	 * @param user    - user to be updated.
	 * @param userDto - new user data.
	 * @return - the updated user.
	 */
	public User updateUser(User user, UserDto userDto) {
		user.setUsername(userDto.getUsername());
		user.setEmail(userDto.getEmail());
		user.setPassword(hashPassword(userDto.getPassword()));
		return userRepo.saveAndFlush(user);
	}

	/**
	 * Saves and flushes the user entity.
	 * 
	 * @param user - user to be saved.
	 * @return - the saved user.
	 */
	public User updateUser(User user) {
		return this.userRepo.saveAndFlush(user);
	}

	/**
	 * Handles deleting a user by id.
	 * 
	 * @param userId - int representing user id to be deleted.
	 * @return - true if successful.
	 */
	public Boolean deleteUser(int userId) {
		userRepo.deleteById(userId);
		return true;
	}

	private String hashPassword(String password) {
		StringBuilder result = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes());
			byte[] bytes = md.digest();
			for (int i = 0; i < bytes.length; i++) {
				result.append((char) bytes[i]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

}
