package com.todoweb.api.dto.user;

import com.todoweb.api.domain.user.Users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserResponseDTO {

	private String email;
	private String message;
	private Boolean isError; 
	
	public static UserResponseDTO fromEntity(final Users entity, String message, Boolean isError) {
		return UserResponseDTO.builder()
				.email(entity.getEmail())
				.message(message)
				.isError(isError)
				.build();
	}
}
