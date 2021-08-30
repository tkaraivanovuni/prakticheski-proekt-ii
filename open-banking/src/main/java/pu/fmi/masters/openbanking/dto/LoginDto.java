package pu.fmi.masters.openbanking.dto;

/**
 * This class defines a data transfer object used for deserializing JSON data.
 */
public class LoginDto {
	
	private String username;
	private String password;
	
	/**
	 * No-arguments constructor.
	 */
	public LoginDto() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
