package com.todoweb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoweb.api.domain.user.UserRepository;
import com.todoweb.api.domain.user.Users;
import com.todoweb.api.dto.todo.TodoRequestDTO;
import com.todoweb.api.dto.todo.TodoResponseDTO;
import com.todoweb.api.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/fetchAllTodosByUser")
	public ResponseEntity<?> fetchTodos(){
		try {
			
			String testEmail = "test@test.com";
			Users testUser = userRepository.findByEmail(testEmail);
			
			List<TodoResponseDTO> fetchedtodos = todoService.fetchAllTodosByUser(testUser);
			
			return ResponseEntity.ok().body(fetchedtodos);
		}
		catch(RuntimeException e) {
			log.debug("While fetchAllTodosByUser... RuntimeException msg: {}", e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/createTodo")
	public ResponseEntity<?> createTodo(@RequestBody TodoRequestDTO dto){
		try {
			TodoResponseDTO createdtodos = todoService.create(dto);
			return ResponseEntity.ok().body(createdtodos);
		}
		catch(RuntimeException e) {
			log.debug("Whiel createTodo ... RuntimeException msg: {}", e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.badRequest().build();
		}
	}
}
