package pu.fmi.masters.openbanking.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pu.fmi.masters.openbanking.configuration.UserPrincipal;
import pu.fmi.masters.openbanking.model.User;
import pu.fmi.masters.openbanking.repository.UserRepo;

/**
 * This class defines methods for the retrieval of user-related data.
 */
@Service
public class AppUserDetailService implements UserDetailsService {

	private UserRepo userRepo;

	/**
	 * Constructor.
	 * 
	 * @param userRepo - {@link UserRepo} for data access.
	 */
	@Autowired
	public AppUserDetailService(UserRepo userRepo) {
		this.userRepo = userRepo;
	}

	/**
	 * This method returns a new {@link UserPrincipal} for authorization.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> optionalUser = userRepo.findUserByUsername(username);
		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		return new UserPrincipal(optionalUser.get());
	}

}
