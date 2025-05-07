package com.todoweb.api.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoweb.api.todo.dto.TodoRequestDTO;
import com.todoweb.api.todo.dto.TodoResponseDTO;
import com.todoweb.api.todo.service.TodoService;
import com.todoweb.api.user.entity.Users;
import com.todoweb.api.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@Autowired
	private UserRepository userRepository;

	@PostMapping("/fetchAllTodos")
	public ResponseEntity<?> fetchAllTodos(@RequestBody TodoRequestDTO dto){
		try {
			
			Users user = userRepository.findByEmail(dto.getEmail())
					.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
			
			List<TodoResponseDTO> fetchedTodos = todoService.fetchAllTodos(user);
			
			return ResponseEntity.ok().body(fetchedTodos);
		}
		catch(RuntimeException e) {
			log.debug("While fetchAllTodos... error: {}", e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
		}
	}
	
	@PostMapping("/fetchTodosByDate")
	public ResponseEntity<?> fetchTodosByDate(@RequestBody TodoRequestDTO dto){
		try {
			log.info("fetchTodosByDate dto.getEmail: {}", dto.getEmail());
			
			List<TodoResponseDTO> fetchedTodos = todoService.fetchTodosByDate(dto);
			
			return ResponseEntity.ok().body(fetchedTodos);
		}
		catch(RuntimeException e) {
			log.debug("While fetchTodosByDate... error: {}", e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INTERNAL_SERVER_ERROR");
		}
	}
	
	
	
	@PostMapping("/createTodo")
	public ResponseEntity<?> createTodo(@RequestBody TodoRequestDTO dto){
		try {
			TodoResponseDTO createdTodos = todoService.create(dto);
			
			return ResponseEntity.ok().body(createdTodos);
		}
		catch(RuntimeException e) {
			
			log.debug("While createTodo ... error: {}", e.getMessage());
			e.printStackTrace();
			
			return ResponseEntity.badRequest().build();
		}
	}
	
	@PostMapping("/updateTodo")
	public ResponseEntity<?> updateTodo(@RequestBody TodoRequestDTO dto){
		
		try {
			
			TodoResponseDTO updatedTodos = todoService.update(dto);
			
			log.debug("While updateTodo ... updatedTodos: {}", updatedTodos);
			
			return ResponseEntity.ok().body(updatedTodos);
		}
		catch(RuntimeException e) {
			log.debug("While updateTodo ... error: {}", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
	}
	
	@PostMapping("/deleteTodo")
	public ResponseEntity<?> deleteTodo(@RequestBody TodoRequestDTO dto){
		try {
			List<TodoResponseDTO> deletedList = todoService.delete(dto);
			
			return ResponseEntity.ok().body(deletedList);
		}
		catch(RuntimeException e) {
			log.debug("While deleteTodo ... error: {}", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
		
	}
}
