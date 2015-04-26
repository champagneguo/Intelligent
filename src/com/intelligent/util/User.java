package com.intelligent.util;

public class User {

	private String Username;
	private String Password;
	private String Email;
	private String Phone;
	private int Position_id;

	public User(String Username, String Password, String Email, String Phone, int number) {
		this.Username = Username;
		this.Password = Password;
		this.Email = Email;
		this.Phone = Phone;
		this.setPosition_id(number);
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String Username) {
		this.Username = Username;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String Password) {
		this.Password = Password;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmial(String Email) {
		this.Email = Email;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String Phone) {
		this.Phone = Phone;
	}

	public int getPosition_id() {
		return Position_id;
	}

	public void setPosition_id(int position_id) {
		Position_id = position_id;
	}

}
