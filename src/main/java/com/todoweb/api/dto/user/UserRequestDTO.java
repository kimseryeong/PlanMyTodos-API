package com.todoweb.api.dto.user;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

/**
 * 사용자 요청 DTO
 */

@Builder
@Data
@Getter
public class UserRequestDTO {
	private String email;
	private String password;

	@Builder
	public UserRequestDTO(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
