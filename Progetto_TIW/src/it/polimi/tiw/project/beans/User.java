package it.polimi.tiw.project.beans;

import java.sql.Blob;

public class User{
	private int id;
	private String username;
	private String email;
	private String password;
	private String experience;
	private String role;
	private Blob image;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
	
	public String getExperience() {
		return experience;
	}
	
	public void setExperience(String experience) {
		this.experience = experience;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public Blob getImage() {
		return image;
	}
	
	public void setImage(Blob image) {
		this.image = image;
	}
}

