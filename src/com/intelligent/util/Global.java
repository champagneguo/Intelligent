package com.intelligent.util;

import android.app.Application;

public class Global extends Application {

	private String username;

	public Global() {

	}

	public Global(String username) {
		this.setUsername(username);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
