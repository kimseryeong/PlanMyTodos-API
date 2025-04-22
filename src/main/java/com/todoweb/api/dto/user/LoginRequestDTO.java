package com.todoweb.api.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 로그인 요청 DTO
 */

@Builder
@Getter
@Setter
@NoArgsConstructor
public class LoginRequestDTO {
	private String email;
	private String password;

	@Builder
	public LoginRequestDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
}
