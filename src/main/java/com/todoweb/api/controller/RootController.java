package com.todoweb.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

	@GetMapping("/")
    public ResponseEntity<?> root() {
		Map<String, Object> response = new HashMap<>();
		
		response.put("status", 200);
		response.put("message", "This is Root Path");
		
		return ResponseEntity.ok().body(response);
    }
}
