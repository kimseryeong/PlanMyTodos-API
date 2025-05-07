package com.todoweb.api.user.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.todoweb.api.common.auth.Role;
import com.todoweb.api.common.entity.BaseTimeEntity;
import com.todoweb.api.user.dto.LoginRequestDTO;
import com.todoweb.api.user.dto.SignUpRequestDTO;
import com.todoweb.api.todo.entity.Todos;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Users extends BaseTimeEntity{

	@Id 
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(nullable = false)
	private UUID uuid;
	
	@Column(nullable = false, unique = true)
	private String email;
	
	private String password;
	
	@Column(name = "login_type")
	@Enumerated(EnumType.STRING)
	private LoginType loginType; //GOOGLE, LOCAL
	
	private String token;
	
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
	private Role role;
	
	@OneToMany(mappedBy = "users", cascade = CascadeType.PERSIST)
	private List<Todos> todoList = new ArrayList<>();
	
	@Builder
	public Users(UUID uuid, String email, String password, LoginType loginType, Role role) {
		this.uuid = uuid;
		this.email = email;
		this.password = password;
		this.loginType = loginType;
		this.role = role;
	}
	
	public Users(SignUpRequestDTO dto) {
		this.email = dto.getEmail();
		this.password = dto.getPassword();
	}
	
	public Users(LoginRequestDTO dto) {
		this.email = dto.getEmail();
		this.password = dto.getPassword();
	}
	
	public String getRoleKey() {
        return this.role.getKey();
    }
}
