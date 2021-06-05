package pu.fmi.masters.openbanking.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;

/**
 * This class handles viewing and updating user information.
 */
@RestController
public class UserController {

	private UserRepo userRepo;

	/**
	 * Constructor.
	 * 
	 * @param userRepo                 - user repository for data accesss.
	 * @param webSecurityConfiguration - auhentication.
	 */
	public UserController(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * This method handles requests for getting user data.
	 * 
	 * @param session - the current session.
	 * @return - {@link User} data.
	 */
	@GetMapping(path = "/profile")
	public User getUserInfo(HttpSession session) {

		User user = (User) session.getAttribute("user");

		return user;
	}

	/**
	 * This method handles updating user data.
	 * 
	 * @param username       - new username.
	 * @param email          - new email.
	 * @param password       - new password.
	 * @param repeatPassword - repeated new password.
	 * @param session        - the current session.
	 * @return - response entity of the outcome.
	 */
	@PutMapping(path = "/profile")
	public ResponseEntity<Boolean> updateUserInfo(@RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword, HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			if (password.equals(repeatPassword)) {
				user.setUsername(username);
				user.setEmail(email);
				user.setPassword(hashPassword(password));
				userRepo.saveAndFlush(user);
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}

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
