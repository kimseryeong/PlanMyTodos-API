package com.todoweb.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todoweb.api.domain.todo.TodoRespository;
import com.todoweb.api.dto.TodoDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {

	private final TodoRespository todoRespository;
	
	@Transactional(readOnly = true)
	public List<TodoDTO> fetch(){
		return todoRespository.findAll()
				.stream()
				.map(TodoDTO::new)
				.collect(Collectors.toList());
	}
}
