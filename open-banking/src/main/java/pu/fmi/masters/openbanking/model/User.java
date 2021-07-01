package pu.fmi.masters.openbanking.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

/**
 * This class provides the main characteristics of the {@link User} data
 * model.
 */
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(nullable = false, unique = true)
	private String username;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;
	
	@Enumerated
	private Role role;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonBackReference
	private Set<BankAccount> accounts;

	/**
	 * No arguments constructor.
	 */
	public User() {
	}

	/**
	 * Username and email constructor.
	 * 
	 * @param username - string representing username.
	 * @param email    - string representing email.
	 */
	public User(String username, String email) {
		this.username = username;
		this.email = email;
	}

	/**
	 * Username, password and email constructor.
	 * 
	 * @param username - string representing username.
	 * @param password - string representing password.
	 * @param email    - string representing email.
	 */
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	/**
	 * This method returns the user's id.
	 * 
	 * @return - int representing id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method sets the user's id.
	 * 
	 * @param id - new int to be set as id.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method returns the user's username.
	 * 
	 * @return - string representing current username.
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * This method sets the username.
	 * 
	 * @param username - string to be set as new username.
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * This method returns the password.
	 * 
	 * @return - string representing password.
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * This method sets the password.
	 * 
	 * @param password - string to be set as password.
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * This method returns the email.
	 * 
	 * @return - string representing email.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * This method sets the email.
	 * 
	 * @param email - string to be set as new email.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * This method returns the list of accounts.
	 * 
	 * @return - List of {@link BankAccount} the user has.
	 */
	public Set<BankAccount> getAccounts() {
		return accounts;
	}

	/**
	 * This method sets the list of accounts.
	 * 
	 * @param accounts - List of {@link BankAccount} to be set as new account
	 *                 list.
	 */
	public void setAccounts(Set<BankAccount> accounts) {
		this.accounts = accounts;
	}

	/**
	 * This method returns the user's role.
	 * 
	 * @return - {@link Role} representing the role.
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * This method sets the user's role.
	 * 
	 * @param role - {@link Role} to be set as new role.
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}
