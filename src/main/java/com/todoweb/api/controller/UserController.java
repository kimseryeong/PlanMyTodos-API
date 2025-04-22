package com.todoweb.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoweb.api.dto.user.LoginRequestDTO;
import com.todoweb.api.dto.user.SignUpRequestDTO;
import com.todoweb.api.dto.user.UserResponseDTO;
import com.todoweb.api.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController<T> {

	@Autowired
	private UserService userService;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO dto){
		try {
			UserResponseDTO response = userService.signUp(dto);
			log.info("signup ... response: {}", response);
			
			return ResponseEntity.ok().body(response);
		}
		catch(RuntimeException e) {
			log.error("signup ... exception: {}", e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto){
		try {
			UserResponseDTO response = userService.login(dto);
			return ResponseEntity.ok().body(response);
		}
		catch(RuntimeException e) {
			log.error("signup ... exception: {}", e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
		}
	}
}
