package com.todoweb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	

	String testEmail = "test@test.com";
	
	@GetMapping("/fetchAllTodos")
	public ResponseEntity<?> fetchAllTodos(@RequestParam("email") String email){
		try {
			
			Users user = userRepository.findByEmail(email);
			
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
	
	@DeleteMapping("/deleteTodo/{id}")
	public ResponseEntity<?> deleteTodo(@PathVariable("id") Long id){
		try {
			List<TodoResponseDTO> deletedList = todoService.delete(id, testEmail);
			
			return ResponseEntity.ok().body(deletedList);
		}
		catch(RuntimeException e) {
			log.debug("While deleteTodo ... error: {}", e.getMessage());
			e.printStackTrace();
			
			return null;
		}
		
	}
}
