package pu.fmi.masters.openbanking.controller;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.openqa.selenium.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pu.fmi.masters.openbanking.WebSecurityConfiguration;
import pu.fmi.masters.openbanking.model.Role;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;

/**
 * This class controls user registration and login.
 */
@RestController
public class LoginController {

	private UserRepo userRepo;
	private WebSecurityConfiguration webSecurityConfiguration;

	/**
	 * Constructor.
	 * 
	 * @param userRepo                 - {@link User} repository for data
	 *                                 access.
	 * @param roleRepo                 - {@link Role} repository for
	 *                                 authorization.
	 * @param webSecurityConfiguration - for authentication.
	 */
	@Autowired
	public LoginController(UserRepo userRepo, WebSecurityConfiguration webSecurityConfiguration) {
		this.userRepo = userRepo;
		//this.roleRepo = roleRepo;
		this.webSecurityConfiguration = webSecurityConfiguration;
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
	@PostMapping(path = "/user")
	public String registerNewUser(@RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword) {

		if (password.equals(repeatPassword)) {

			User user = new User(username, hashPassword(password), email);
//			Optional<Role> optionalRole = roleRepo.findByRole("USER");
//			if (!optionalRole.isPresent()) {
//				throw new InvalidArgumentException("Invalid role");
//			}
			user.setRole(Role.USER);

			try {
				userRepo.saveAndFlush(user);
			} catch (RuntimeException e) {
				return "Registration failed!";
			}
			return "Registration successful! Please log in!";
		}
		return "Passwords do not match!";

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
	public String registerNewAdmin(@RequestParam(value = "username") String username,
			@RequestParam(value = "email") String email, @RequestParam(value = "password") String password,
			@RequestParam(value = "repeatPassword") String repeatPassword) {

		if (password.equals(repeatPassword)) {

			User user = new User(username, hashPassword(password), email);
//			Optional<Role> optionalRole = roleRepo.findByRole("ADMIN");
//			if (!optionalRole.isPresent()) {
//				throw new InvalidArgumentException("Invalid role");
//			}
			user.setRole(Role.ADMIN);

			try {
				userRepo.saveAndFlush(user);
			} catch (RuntimeException e) {
				return "Registration failed!";
			}
			return "Registration successful! Please log in!";
		}
		return "Passwords do not match!";

	}

	/**
	 * This method handles user login.
	 * 
	 * @param username - string representing username.
	 * @param password - string representing password.
	 * @param session  - current session.
	 * @return - string representing url to redirect to.
	 */
	@GetMapping(path = "/user")
	public String login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpSession session) {
		Optional<User> optionalUser = userRepo.findUserByUsernameAndPassword(username, hashPassword(password));
		if(!optionalUser.isPresent()) {
			throw new InvalidArgumentException("Wrong username or password!");
		}
		User user = optionalUser.get();
		session.setAttribute("user", user);
		try {
			UserDetails userDetails = webSecurityConfiguration.userDetailsServiceBean()
					.loadUserByUsername(user.getUsername());

			if (userDetails != null) {

				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
						userDetails.getPassword(), userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(auth);

				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
						.currentRequestAttributes();

				HttpSession http = attr.getRequest().getSession(true);

				http.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

			}

			return "profile.html";

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	return "error.html";

	}
	
	/**
	 * This method handles user login.
	 * 
	 * @param username - string representing username.
	 * @param password - string representing password.
	 * @param session  - current session.
	 * @return - string representing url to redirect to.
	 */
	@GetMapping(path = "/admin")
	public String loginAdmin(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password, HttpSession session) {
		Optional<User> optionalUser = userRepo.findUserByUsernameAndPassword(username, hashPassword(password));
		if(!optionalUser.isPresent()) {
			throw new InvalidArgumentException("Wrong username or password!");
		}
		User user = optionalUser.get();
		session.setAttribute("user", user);
		try {
			UserDetails userDetails = webSecurityConfiguration.userDetailsServiceBean()
					.loadUserByUsername(user.getUsername());

			if (userDetails != null) {

				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
						userDetails.getPassword(), userDetails.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(auth);

				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
						.currentRequestAttributes();

				HttpSession http = attr.getRequest().getSession(true);

				http.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

			}

			return "banks.html";

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "error.html";

	}

	/**
	 * This method returns the user id and the response status.
	 * 
	 * @param session - the current session.
	 * @return - int representing id and a response status.
	 */
	@GetMapping(path = "/authorization")
	public ResponseEntity<Integer> authorizeMe(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			return new ResponseEntity<>(user.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
		}

	}

	/**
	 * This method logs the current user out.
	 * 
	 * @param session - the current session
	 * @return - boolean representing the outcome and response status.
	 */
	@DeleteMapping(path = "/session")
	public ResponseEntity<Boolean> logout(HttpSession session) {

		User user = (User) session.getAttribute("user");

		if (user != null) {
			session.invalidate();
			return new ResponseEntity<>(true, HttpStatus.OK);
		}

		return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

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
