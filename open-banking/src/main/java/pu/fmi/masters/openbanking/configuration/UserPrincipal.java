package pu.fmi.masters.openbanking.configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pu.fmi.masters.openbanking.model.Role;
import pu.fmi.masters.openbanking.model.User;

/**
 * This class represents the currently logged user and provides methods to to
 * configure authorization.
 */
public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 3672084989320118313L;

	private User user;
	private Set<GrantedAuthority> authorities;

	/**
	 * Constructor.
	 * 
	 * @param user - the logged user.
	 */
	public UserPrincipal(User user) {
		this.user = user;
		authorities = new HashSet<GrantedAuthority>();
		insertRoles();
	}

	private void insertRoles() {
		if (user.getRole() == null) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}

		if (authorities.isEmpty()) {
			authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
		}
		if (user.getRole().equals(Role.ADMIN)) {
			authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
	}

	/**
	 * This method returns the user's authorities.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * This method returns the user's password.
	 */
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	/**
	 * This method returns the user's username.
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * This method returns the the status of the account in terms of expiration.
	 * True is returned by default as the application does not implement expiration.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * This method returns the the status of the account in terms of accessibility.
	 * True is returned by default as the application does not implement locking of
	 * accounts.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * This method returns the the status of the account's credentials. True is
	 * returned by default as the application does not implement expiration of
	 * credentials.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * This method returns the the status of the account. True is returned by
	 * default as the application does not implement account disabling.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

}
