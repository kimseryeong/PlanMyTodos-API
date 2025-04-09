package com.todoweb.api.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todoweb.api.domain.todo.TodoRespository;
import com.todoweb.api.domain.todo.Todos;
import com.todoweb.api.domain.user.UserRepository;
import com.todoweb.api.domain.user.Users;
import com.todoweb.api.dto.todo.TodoRequestDTO;
import com.todoweb.api.dto.todo.TodoResponseDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TodoService {

    private final UserRepository userRepository;

	private final TodoRespository todoRespository;
	
	/**
	 * 조회
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TodoResponseDTO> fetch(Users users){
		
		return todoRespository.findByUsers(users)
				.stream()
				.map(TodoResponseDTO::fromEntity)
				.collect(Collectors.toList());
	}
	
	/**
	 * 등록
	 * @param todo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public List<TodoResponseDTO> create(TodoRequestDTO dto){
		
		try {
			//test user 객체 임의 생성
			Users testUser = Users.builder()
					.email("test@test.com")
					.password("1234")
					.build();
			
			Users savedUser = userRepository.save(testUser);
			log.debug("savedUser: {}", savedUser);
			
			Todos entity = Todos.builder()
					.title(dto.getTitle())
					.content(dto.getContent())
					.completed(dto.isCompleted())
					.startAt(dto.getStartAt())
					.endAt(dto.getEndAt())
					.users(savedUser)
					.build();
			
			log.debug("create entity: {}", entity);
			
			todoRespository.save(entity);
			
			return fetch(testUser);
		}
		catch(Exception e) {
			log.debug("error: {}", e.getMessage());
			log.error("error during save: ", e); // 전체 스택 찍어보기!
		    return null;
		}
		
	}
}
