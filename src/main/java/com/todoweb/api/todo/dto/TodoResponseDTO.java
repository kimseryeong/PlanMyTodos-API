package com.todoweb.api.todo.dto;

import java.time.LocalDate;

import com.todoweb.api.todo.entity.Todos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class TodoResponseDTO {

	private Long id; 
	private String title;
	private boolean completed;
	private String content;
	private LocalDate startAt;
	private LocalDate endAt;
		
	
	//Entity -> DTO
	public static TodoResponseDTO fromEntity(final Todos entity) {
		return TodoResponseDTO.builder()
				.id(entity.getId())
		        .title(entity.getTitle())
		        .completed(entity.isCompleted())
		        .content(entity.getContent())
		        .startAt(entity.getStartAt())
		        .endAt(entity.getEndAt())
		        .build();
	}
	
}
