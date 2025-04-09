package com.todoweb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	//private final UserRepository userRepository;
	
	@GetMapping("/fetch")
	public ResponseEntity<?> fetchTodos(){
		try {
			
			
			//List<TodoDTO> fetchedtodos = todoService.fetch();
			return ResponseEntity.ok().body(null);
		}
		catch(RuntimeException e) {
			log.debug("RuntimeException msg: {}", e.getMessage());
			
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createTodo(@RequestBody TodoRequestDTO dto){
		try {
			List<TodoResponseDTO> createdtodos = todoService.create(dto);
			return ResponseEntity.ok().body(createdtodos);
		}
		catch(RuntimeException e) {
			log.debug("RuntimeException msg: {}", e.getMessage());
			
			return ResponseEntity.badRequest().build();
		}
	}
}
