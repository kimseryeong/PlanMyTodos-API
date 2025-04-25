package com.todoweb.api.dto.todo;

import java.time.LocalDate;

import com.todoweb.api.domain.todo.Todos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class TodoDTO {

	private Long id;
	private String title;
	private boolean completed;
	private String content;
	private LocalDate startAt;
	private LocalDate endAt;
		
	public TodoDTO(Long id, String title, boolean completed, String content, LocalDate startAt, LocalDate endAt) {
		this.id = id;
		this.title = title;
		this.completed = completed;
		this.content = content;
		this.startAt = startAt;
		this.endAt = endAt;
	}
	
	//TodoDTO::new 사용하기 위해 Entity -> DTO 변환하는 생성자
	public TodoDTO(Todos entity) {
		this.title = entity.getTitle();
		this.completed = entity.isCompleted();
		this.content = entity.getContent();
		this.startAt = entity.getStartAt();
		this.endAt = entity.getEndAt();
		
	}
}
