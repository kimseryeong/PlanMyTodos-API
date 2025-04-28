package com.todoweb.api.domain.todo;

import java.time.LocalDate;

import com.todoweb.api.domain.BaseTimeEntity;
import com.todoweb.api.domain.user.Users;
import com.todoweb.api.dto.todo.TodoRequestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Todos extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private boolean completed = false;
	
	private String content;
	
	private LocalDate startAt;
	private LocalDate endAt;
	
	@ManyToOne
	@JoinColumn(name="user_uuid")
	private Users users;
	
	@Builder
	public Todos(String title, boolean completed, String content, LocalDate startAt, LocalDate endAt, Users users) {
		this.title = title;
		this.completed = completed;
		this.content = content;
		this.startAt = startAt;
		this.endAt = endAt;
		this.users = users;
	}

	public void updateInfo(TodoRequestDTO dto) {
		if(dto.getTitle() != null) this.title = dto.getTitle();
		if(dto.getContent() != null) this.content = dto.getContent();
		if(dto.getStartAt() != null) this.startAt = dto.getStartAt();
		if(dto.getEndAt() != null) this.endAt = dto.getEndAt();
		this.completed = dto.isCompleted();
	}
	
}
