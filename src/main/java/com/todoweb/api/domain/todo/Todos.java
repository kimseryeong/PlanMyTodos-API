package com.todoweb.api.domain.todo;

import java.time.LocalDateTime;

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
	
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	
	@ManyToOne
	@JoinColumn(name="user_uuid")
	private Users users;
	
	@Builder
	public Todos(String title, boolean completed, String content, LocalDateTime startAt, LocalDateTime endAt, Users users) {
		this.title = title;
		this.completed = completed;
		this.content = content;
		this.startAt = startAt;
		this.endAt = endAt;
		this.users = users;
	}

	public void updateInfo(TodoRequestDTO dto) {
		this.title = dto.getTitle();
		this.completed = dto.isCompleted();
		this.content = dto.getContent();
		this.startAt = dto.getStartAt();
		this.endAt = dto.getEndAt();
	}
	
}
