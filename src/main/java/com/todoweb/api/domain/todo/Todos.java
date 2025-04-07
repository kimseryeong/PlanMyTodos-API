package com.todoweb.api.domain.todo;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;

import com.todoweb.api.domain.user.Users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Todos {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	@ColumnDefault("false")
	private boolean completed;
	
	private String content;
	
	private LocalDateTime startAt;
	private LocalDateTime endAt;
	
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	@ManyToOne
	@JoinColumn(name="user_uuid")
	private Users users;
	
	@Builder
	public Todos(String email, String title, boolean completed, String content, LocalDateTime startAt, LocalDateTime endAt) {
		this.email = email;
		this.title = title;
		this.completed = completed;
		this.content = content;
		this.startAt = startAt;
		this.endAt = endAt;
	}
}
