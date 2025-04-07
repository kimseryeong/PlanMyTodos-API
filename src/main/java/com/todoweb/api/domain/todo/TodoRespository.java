package com.todoweb.api.domain.todo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoweb.api.domain.user.Users;

@Repository
public interface TodoRespository extends JpaRepository<Todos, Long> {

	// user 객체를 기준으로 todolist 조회
	List<Todos> findByUser(Users user);
}
