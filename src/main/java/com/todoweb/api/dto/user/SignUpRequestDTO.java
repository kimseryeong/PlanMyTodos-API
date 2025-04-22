package com.todoweb.api.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {

	private String email;
	private String password;
	private String confirmPassword;
	
	public String getConfirmPassword() {
		return this.confirmPassword;
	}
}
