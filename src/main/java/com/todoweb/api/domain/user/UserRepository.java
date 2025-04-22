package com.todoweb.api.domain.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

	//유저 존재 여부
	boolean existsByEmail(String email);

	//email로 유저 찾기
	Users findByEmail(String email);
	
}
