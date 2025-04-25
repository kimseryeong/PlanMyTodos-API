package com.todoweb.api.domain.todo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.todoweb.api.domain.user.Users;

@Repository
public interface TodoRespository extends JpaRepository<Todos, Long> {

	// user 객체를 기준으로 todolist 조회
	List<Todos> findByUsers(Users users);

	@Query("SELECT t FROM Todos t WHERE t.users = :users AND t.startAt <= :currentAt AND t.endAt >= :currentAt")
	List<Todos> findTodosByDate(@Param("users") Users users, @Param("currentAt") LocalDate currentAt);

}
