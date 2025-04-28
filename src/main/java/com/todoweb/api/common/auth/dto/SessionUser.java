package com.todoweb.api.common.auth.dto;

import java.io.Serializable;
import java.util.UUID;

import com.todoweb.api.domain.user.Users;

import lombok.Getter;

@Getter
public class SessionUser implements Serializable{

	private String email;
	
	public SessionUser(Users user) {
		this.email = user.getEmail();
	}
}
