package pu.fmi.masters.openbanking.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class provides the main characteristics of the {@link Role} data
 * model.
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

	private static final long serialVersionUID = 8080634750286187993L;

	@Id
	@Column(name = "role_id")
	@GeneratedValue
	private int id;

	@Column(name = "role_name", unique = true, nullable = false)
	private String role;

	/**
	 * No arguments constructor.
	 */
	public Role() {

	}

	/**
	 * Constructor.
	 * 
	 * @param role - role name to be set.
	 */
	public Role(String role) {
		this.role = role;
	}

	/**
	 * This method returns the role's id.
	 * 
	 * @return - int representing the id.
	 */
	public int getId() {
		return id;
	}

	/**
	 * This method sets the role's id.
	 * 
	 * @param id - new id to be set.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * This method returns the role's name.
	 * 
	 * @return - string representing the role's name.
	 */
	public String getRole() {
		return role;
	}

	/**
	 * This method set's the role's name.
	 * 
	 * @param role - new role name to be set.
	 */
	public void setRole(String role) {
		this.role = role;
	}

}
