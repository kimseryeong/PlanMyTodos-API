package com.todoweb.api.domain.user;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.todoweb.api.domain.todo.Todos;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Users {

	@Id @GeneratedValue(strategy = GenerationType.UUID)
	private UUID uuid;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String password;
	
	@Column(name = "login_type")
	@Enumerated(EnumType.STRING)
	private LoginType loginType; //GOOGLE, LOCAL
	
	private String token;
	
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	private List<Todos> todos = new ArrayList<>();
	
	@Builder
	public Users(String email, String password, LoginType loginType, String token) {
		this.email = email;
		this.password = password;
		this.loginType = loginType;
		this.token = token;
	}
}
