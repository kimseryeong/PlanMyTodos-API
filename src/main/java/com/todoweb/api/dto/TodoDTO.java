package com.todoweb.api.dto;

import java.time.LocalDateTime;

import com.todoweb.api.domain.todo.Todos;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Data
@Getter
public class TodoDTO {

	private String email;
	private String title;
	private boolean completed;
	private String content;
	private LocalDateTime startAt;
	private LocalDateTime endAt;
		
	public TodoDTO(String email, String title, boolean completed, String content, LocalDateTime startAt, LocalDateTime endAt) {
		this.email = email;
		this.title = title;
		this.completed = completed;
		this.content = content;
		this.startAt = startAt;
		this.endAt = endAt;
	}
	
	//TodoDTO::new 사용하기 위해 Entity -> DTO 변환하는 생성자
	public TodoDTO(Todos todo) {
		this.email = todo.getEmail();
		this.title = todo.getTitle();
		this.completed = todo.isCompleted();
		this.content = todo.getContent();
		this.startAt = todo.getStartAt();
		this.endAt = todo.getEndAt();
		
	}
}
