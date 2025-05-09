package com.todoweb.api.user.dto;

import com.todoweb.api.user.entity.Users;

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
	
	public UserResponseDTO(String email) {
		this.email = email;
	}
}
