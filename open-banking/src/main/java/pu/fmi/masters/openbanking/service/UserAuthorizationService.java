package pu.fmi.masters.openbanking.service;

import javax.servlet.http.HttpSession;

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
import pu.fmi.masters.openbanking.model.User;

/**
 * This class handles user authorization.
 */
@Service
public class UserAuthorizationService {

	private final WebSecurityConfiguration webSecurityConfiguration;

	/**
	 * Constructor.
	 * 
	 * @param webSecurityConfiguration - provided configuration.
	 */
	@Autowired
	public UserAuthorizationService(WebSecurityConfiguration webSecurityConfiguration) {
		this.webSecurityConfiguration = webSecurityConfiguration;
	}

	public String auth(User user) {
		try {
			UserDetails userDetails = webSecurityConfiguration.userDetailsServiceBean().loadUserByUsername(user.getUsername());
			if (userDetails != null) {
				Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(auth);
				ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
				HttpSession httpSession = attr.getRequest().getSession(true);
				httpSession.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
				httpSession.setAttribute("user_id", user.getId());
			}
			return "profile.html";
		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "error.html";
	}
	
}
