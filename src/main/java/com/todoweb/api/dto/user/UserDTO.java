package com.todoweb.api.dto.user;


import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class UserDTO {

	private UUID uuid;
	private String email;
	private String password;
	

	@Builder
	public UserDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
}
