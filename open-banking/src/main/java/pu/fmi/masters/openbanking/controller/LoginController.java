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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.server.ResponseStatusException;

import pu.fmi.masters.openbanking.configuration.WebSecurityConfiguration;
import pu.fmi.masters.openbanking.dto.LoginDto;
import pu.fmi.masters.openbanking.model.Role;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;
import pu.fmi.masters.openbanking.service.UserAccountService;

/**
 * This class controls user login.
 */
@RestController
public class LoginController {
	
	private UserAccountService userAccountService;
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
	public LoginController(UserRepo userRepo, WebSecurityConfiguration webSecurityConfiguration, UserAccountService userAccountService) {
		this.userRepo = userRepo;
		this.webSecurityConfiguration = webSecurityConfiguration;
		this.userAccountService = userAccountService;
	}

	/**
	 * This method handles user login.
	 * 
	 * @param username - string representing username.
	 * @param password - string representing password.
	 * @param session  - current session.
	 * @return - string representing url to redirect to.
	 */
	@PostMapping(path = "/session")
	public String login(LoginDto loginDto, HttpSession session) {
		Optional<User> optionalUser = userRepo.findUserByUsernameAndPassword(loginDto.getUsername(), hashPassword(loginDto.getPassword()));
		if(!optionalUser.isPresent()) {
			throw new InvalidArgumentException("Wrong username or password!");
		}
		User user = optionalUser.get();
		session.setAttribute("user_id", user.getId());
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
	@PostMapping(path = "/session/admin")
	public String loginAdmin(LoginDto loginDto, HttpSession session) {
		Optional<User> optionalUser = userRepo.findUserByUsernameAndPassword(loginDto.getUsername(), hashPassword(loginDto.getPassword()));
		if(!optionalUser.isPresent()) {
			throw new IllegalArgumentException("Wrong username or password!");
		}
		User user = optionalUser.get();
		if (!user.getRole().equals(Role.ADMIN)) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "More privileges required!");
		}
		session.setAttribute("user_id", user.getId());
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
	public ResponseEntity<Integer> isAuthorized(HttpSession session) {

		int userId = (Integer) session.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);

		if (user != null) {
			return new ResponseEntity<>(user.getId(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
		}

	}
	
	/**
	 * This method returns the user id and the response status.
	 * 
	 * @param session - the current session.
	 * @return - int representing id and a response status.
	 */
	@GetMapping(path = "/authorization/admin")
	public ResponseEntity<Integer> isAdmin(HttpSession session) {

		int userId = (Integer) session.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);

		if (user != null) {
			if (user.getRole().equals(Role.ADMIN)) {
				return new ResponseEntity<>(user.getId(), HttpStatus.OK);
			}
		} 
		return new ResponseEntity<>(0, HttpStatus.UNAUTHORIZED);
	}

	/**
	 * This method logs the current user out.
	 * 
	 * @param session - the current session
	 * @return - boolean representing the outcome and response status.
	 */
	@DeleteMapping(path = "/session")
	public ResponseEntity<Boolean> logout(HttpSession session) {

		int userId = (Integer) session.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);

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
