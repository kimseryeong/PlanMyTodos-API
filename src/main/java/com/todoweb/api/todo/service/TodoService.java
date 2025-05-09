package com.todoweb.api.todo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todoweb.api.todo.dto.TodoRequestDTO;
import com.todoweb.api.todo.dto.TodoResponseDTO;
import com.todoweb.api.todo.entity.Todos;
import com.todoweb.api.todo.repository.TodoRespository;
import com.todoweb.api.user.entity.Users;
import com.todoweb.api.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

	@Autowired
    private final UserRepository userRepository;

	@Autowired
	private final TodoRespository todoRepository;
	
	/**
	 * 전체 목록 조회
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TodoResponseDTO> fetchAllTodos(Users users){
		
		return todoRepository.findByUsers(users)
				.stream()
				.map(TodoResponseDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	/**
	 * 날짜에 따른 목록 조회
	 * @param users
	 * @param date
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TodoResponseDTO> fetchTodosByDate(TodoRequestDTO dto){
		
		
		Users user = userRepository.findByEmail(dto.getEmail()).orElseGet(null);
			//.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
		
		if(user != null) {
			return todoRepository.findTodosByDate(user, dto.getCurrentAt())
					.stream()
					.map(TodoResponseDTO::fromEntity)
					.collect(Collectors.toList());
		}
		else {
			return getSampleTodos(dto.getCurrentAt());
		}
	}
	
	public List<TodoResponseDTO> getSampleTodos(LocalDate currentAt){
		return List.of(
				new TodoResponseDTO("Use PlanMyTodos", "add, modify, delete", currentAt, currentAt, false),
				new TodoResponseDTO("Buy groceries", "carrot, tofu, potato, salmon", currentAt, currentAt, false),
				new TodoResponseDTO("Work out at the fitness center", "1. Running Machine \n2. Squat", currentAt.plusDays(1), currentAt, true),
				new TodoResponseDTO("Clean the house", "", currentAt.plusWeeks(1), currentAt, true),
				new TodoResponseDTO("Take supplements", "vitaminD, biotin, lactobacillus", currentAt.minusDays(5), currentAt, false)
		);
	}
	
	/**
	 * 등록
	 * @param todo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public TodoResponseDTO create(TodoRequestDTO dto){
		
		try {
			
			Users user = userRepository.findByEmail(dto.getEmail())
					.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));
			
			Todos entity = Todos.builder()
					.title(dto.getTitle())
					.content(dto.getContent())
					.completed(dto.isCompleted())
					.startAt(dto.getStartAt())
					.endAt(dto.getEndAt())
					.users(user)
					.build();
			
			log.debug("create entity: {}", entity);
			
			Todos savedEntity = todoRepository.save(entity);

			TodoResponseDTO savedTodo = TodoResponseDTO.fromEntity(savedEntity);
			
			return savedTodo;
		}
		catch(RuntimeException e) {
			log.debug("error: {}", e.getMessage());
			log.error("error during save: ", e); // 전체 스택 찍어보기!
		    return null;
		}
		
	}
	
	/**
	 * 수정
	 * @param dto
	 * @return
	 */
	@Transactional
	public TodoResponseDTO update(TodoRequestDTO dto) {
		try {
			
			Todos todo = todoRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Not found"));
			
			Users todoUser = todo.getUsers();
			
			if(!todoUser.getEmail().equals(dto.getEmail())) {
				new RuntimeException("No Permission User to update");
			}
			
			todo.updateInfo(dto);
			
			return TodoResponseDTO.fromEntity(todo);
			
		}
		catch(RuntimeException e) {
			log.debug("error: {}", e.getMessage());
			log.error("error during save: ", e);
		    return null;
		}
	}
	
	/**
	 * 삭제
	 * @param todoId
	 * @param userEmail
	 * @return
	 */
	@Transactional
	public List<TodoResponseDTO> delete(TodoRequestDTO dto){
		try {
			Todos todo = todoRepository.findById(dto.getId())
					.orElseThrow(() -> new RuntimeException("Not found"));
			
			Users todoUser = todo.getUsers();
			
			if(!todoUser.getEmail().equals(dto.getEmail())) {
				new RuntimeException("No Permission User to update");
			}
			
			todoRepository.deleteById(dto.getId());
			
			return fetchTodosByDate(dto);
			
		}
		catch(RuntimeException e) {
			log.debug("error: {}", e.getMessage());
			log.error("error during save: ", e);
			return null;
		}
	}
	
}
