package com.pizzabox.common.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class represnts the user details
 * @author rupalip
 *
 */
@Entity
@Table(name="user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String LastName;
	
	@Column(name="address")
	private String address;
	
	@Column(name="contact_no")
	private String contactNo;
	
	@Column(name="username")
	private String username;
	
	@Column(name="password")
	private String password;
	
	@Column(name="role")
	private String role;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNo() {
		return contactNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.contactNo = phoneNo;
	}
	
	

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName + ", LastName=" + LastName + ", address=" + address
				+ ", contactNo=" + contactNo + ", username=" + username + ", role=" + role + "]";
	}

	public User(Integer userId, String firstName, String lastName, String address, String contactNo, String username,
			String password, String role) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		LastName = lastName;
		this.address = address;
		this.contactNo = contactNo;
		this.username = username;
		this.password = password;
		this.role = role;
	}

	public User() {
		super();
	}
	
	
}

