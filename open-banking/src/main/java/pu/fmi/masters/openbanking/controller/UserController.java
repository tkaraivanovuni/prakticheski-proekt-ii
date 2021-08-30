package pu.fmi.masters.openbanking.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pu.fmi.masters.openbanking.dto.UserDto;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;
import pu.fmi.masters.openbanking.service.UserAccountService;

/**
 * This class handles requests for generating, retrieving, updating and deleting
 * user information.
 */
@RestController
public class UserController {

	private UserAccountService userAccountService;
	private UserRepo userRepo;

	/**
	 * Constructor.
	 * 
	 * @param userRepo                 - user repository for data access.
	 * @param webSecurityConfiguration - authentication.
	 */
	@Autowired
	public UserController(UserRepo userRepo, UserAccountService userAccountService) {
		this.userRepo = userRepo;
		this.userAccountService = userAccountService;
	}

	/**
	 * This method registers a new user.
	 * 
	 * @param username       - string representation of username.
	 * @param email          - string representation of email.
	 * @param password       - string representation of password.
	 * @param repeatPassword - repeated password.
	 * @return - string representing the outcome.
	 */
//	@PostMapping(path = "/user")
//	public String registerNewUser(@RequestParam(value = "username") String username,
//			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
//			@RequestParam(value = "repeatPassword") String repeatPassword) {
//		if (password.equals(repeatPassword)) {
//			User user = new User(username, hashPassword(password), email);
//			user.setRole(Role.USER);
//			try {
//				userRepo.saveAndFlush(user);
//			} catch (RuntimeException e) {
//				return "Registration failed!";
//			}
//			return "Registration successful! Please log in!";
//		}
//		return "Passwords do not match!";
//	}
	/**
	 * This method handles requests for registering a new user.
	 * 
	 * @param userDto - user data.
	 * @return - response containing the result.
	 */
	@PostMapping(path = "/user")
	public ResponseEntity<User> registerNewUser(UserDto userDto) {
		try {
			return new ResponseEntity<User>(this.userAccountService.addUser(userDto), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists", e);
		}
	}

	/**
	 * This method registers a new admin.
	 * 
	 * @param username       - string representation of username.
	 * @param email          - string representation of email.
	 * @param password       - string representation of password.
	 * @param repeatPassword - repeated password.
	 * @return - string representing the outcome.
	 */
	@PostMapping(path = "/admin")
//	public String registerNewAdmin(@RequestParam(value = "username") String username,
//			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
//			@RequestParam(value = "repeatPassword") String repeatPassword) {
//
//		if (password.equals(repeatPassword)) {
//
//			User user = new User(username, hashPassword(password), email);
//			user.setRole(Role.ADMIN);
//
//			try {
//				userRepo.saveAndFlush(user);
//			} catch (RuntimeException e) {
//				return "Registration failed!";
//			}
//			return "Registration successful! Please log in!";
//		}
//		return "Passwords do not match!";
//	}
	/**
	 * This method handles requests for registering a new administrator.
	 * 
	 * @param userDto - user data.
	 * @return - response containing the result.
	 */
	public ResponseEntity<User> registerNewAdmin(UserDto userDto) {
		try {
			return new ResponseEntity<User>(this.userAccountService.addAdmin(userDto), HttpStatus.CREATED);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists", e);
		}
	}

//	/**
//	 * This method handles updating user data.
//	 * 
//	 * @param username       - new username.
//	 * @param email          - new email.
//	 * @param password       - new password.
//	 * @param repeatPassword - repeated new password.
//	 * @param session        - the current session.
//	 * @return - response entity of the outcome.
//	 */
//	@PutMapping(path = "/profile")
//	public ResponseEntity<Boolean> updateUserInfo(@RequestParam(value = "username") String username,
//			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
//			@RequestParam(value = "repeatPassword") String repeatPassword, HttpSession session) {
//
//		User user = (User) session.getAttribute("user");
//
//		if (user != null) {
//			if (password.equals(repeatPassword)) {
//				user.setUsername(username);
//				user.setEmail(email);
//				user.setPassword(hashPassword(password));
//				userRepo.saveAndFlush(user);
//				return new ResponseEntity<>(true, HttpStatus.OK);
//			} else {
//				return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
//			}
//		} else {
//			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
//		}
//
//	}
	
	@PutMapping(path = "/profile")
	public ResponseEntity<User> updateUser(@RequestBody UserDto userDto, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("user");
		if (user != null) {
			try {
				return new ResponseEntity<User>(this.userAccountService.updateUser(user, userDto), HttpStatus.OK);
			} catch (DataIntegrityViolationException e) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists", e);
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You need to log in first!");		
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
