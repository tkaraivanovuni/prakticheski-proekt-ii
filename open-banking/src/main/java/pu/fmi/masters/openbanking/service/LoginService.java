package pu.fmi.masters.openbanking.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.openqa.selenium.InvalidArgumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import pu.fmi.masters.openbanking.configuration.WebSecurityConfiguration;
import pu.fmi.masters.openbanking.dto.LoginDto;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;

/**
 * This class provides methods to login and logout.
 */
@Service
public class LoginService {
	
	private final UserRepo userRepo;
	private final WebSecurityConfiguration webSecurityConfiguration;
	private final UserAuthorizationService userAuthorizationService;
	
	@Autowired
	public LoginService(UserRepo userRepo, WebSecurityConfiguration webSecurityConfiguration, UserAuthorizationService userAuthorizationService) {
		this.userRepo = userRepo;
		this.webSecurityConfiguration = webSecurityConfiguration;
		this.userAuthorizationService = userAuthorizationService;
	}
	
	public User logUserIn(LoginDto loginDto) {
		Optional<User> optionalUser = userRepo.findUserByUsernameAndPassword(loginDto.getUsername(), hashPassword(loginDto.getPassword()));
		if(!optionalUser.isPresent()) {
			throw new InvalidArgumentException("Wrong username or password!");
		}
		User user = optionalUser.get();
		this.userAuthorizationService.auth(user);
		return user;
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
