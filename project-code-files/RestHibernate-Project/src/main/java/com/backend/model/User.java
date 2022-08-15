package com.backend.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonProperty;

@MappedSuperclass
public class User  implements Serializable
{
	@JsonProperty("User_Email")
	private String userEmail;
	
	@JsonProperty("password")
	private String password;
	
	public User(String userEmail, String password) {
		super();
		this.userEmail = userEmail;
		this.password = password;
	}
	public User() {
		
	}
	
	public User(User user) {
		this.setUserEmail(user.getUserEmail());
		this.setPassword(user.getPassword());
	}
	
	
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userName) {
		this.userEmail = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	@Override
	public boolean equals(Object s) {
		User usr = (User) s;
		if(this.getUserEmail().equals(usr.getUserEmail()) && this.getPassword().equals(usr.getPassword()) ) 
			return true;
		return false;
	}
	
	
	
}