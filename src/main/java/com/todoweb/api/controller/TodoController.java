package com.todoweb.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoweb.api.dto.TodoDTO;
import com.todoweb.api.service.TodoService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/todo")
public class TodoController {

	@Autowired
	private TodoService todoService;
	
	@GetMapping("/fetch")
	public ResponseEntity<?> fetchTodos(){
		try {
			
			List<TodoDTO> todos = todoService.fetch();
			return ResponseEntity.ok().body(todos);
		}
		catch(RuntimeException e) {
			log.debug("RuntimeException msg: {}", e.getMessage());
			
			return ResponseEntity.badRequest().build();
		}
	}
}
