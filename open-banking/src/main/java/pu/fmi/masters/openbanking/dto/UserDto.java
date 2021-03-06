package pu.fmi.masters.openbanking.dto;

/**
 * This class defines a data transfer object used for deserializing JSON data
 * for user registration.
 */
public class UserDto {

	private String username;
	private String email;
	private String password;
	private String repeatPassword;

	/**
	 * No-arguments constructor.
	 */
	public UserDto() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRepeatPassword() {
		return repeatPassword;
	}

	public void setRepeatPassword(String repeatPassword) {
		this.repeatPassword = repeatPassword;
	}

}
