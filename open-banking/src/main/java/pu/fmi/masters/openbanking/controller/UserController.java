package pu.fmi.masters.openbanking.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import pu.fmi.masters.openbanking.dto.UserDto;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.service.UserAccountService;

/**
 * This class handles requests for generating, retrieving, updating and deleting
 * user information.
 */
@RestController
public class UserController {

	private UserAccountService userAccountService;
	/**
	 * Constructor.
	 * 
	 * @param userRepo                 - user repository for data access.
	 * @param webSecurityConfiguration - authentication.
	 */
	@Autowired
	public UserController(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

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
	 * This method handles requests for getting user data.
	 * 
	 * @param session - the current session.
	 * @return - {@link User} data.
	 */
	@GetMapping(path = "/user")
	public User getUserInfo(HttpSession session) {
		int userId = (Integer) session.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);
		return user;
	}
	
	/**
	 * Handles requests for updating user data.
	 * 
	 * @param userDto - user data.
	 * @param httpSession - the current session.
	 * @return - response entity containing the updated user.
	 */
	@PutMapping(path = "/user")
	public ResponseEntity<User> updateUser(UserDto userDto, HttpSession httpSession) {
		int userId = (Integer) httpSession.getAttribute("user_id");
		User user = userAccountService.retrieveById(userId);
		if (user != null) {
			try {
				return new ResponseEntity<User>(this.userAccountService.updateUser(user, userDto), HttpStatus.OK);
			} catch (DataIntegrityViolationException e) {
				throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists", e);
			}
		}
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You need to log in first!");		
	}
	
	/**
	 * Handles requests for deleting a user.
	 * 
	 * @param httpSession - current 
	 * @return
	 */
	@DeleteMapping(path = "/user")
	public Boolean deleteUser(HttpSession httpSession) {
		int userId = (Integer) httpSession.getAttribute("user_id");
		if (this.userAccountService.deleteUser(userId)) {
			httpSession.invalidate();
			return true;
		}
		return false;
	}

}
