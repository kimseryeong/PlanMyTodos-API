package com.todoweb.api.dto.todo;

import java.time.LocalDateTime;

import com.todoweb.api.domain.todo.Todos;

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
	private LocalDateTime startAt;
	private LocalDateTime endAt;
		
	
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
