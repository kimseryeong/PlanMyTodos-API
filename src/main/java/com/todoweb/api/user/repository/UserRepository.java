package com.todoweb.api.user.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todoweb.api.user.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

	//유저 존재 여부
	boolean existsByEmail(String email);
	
	Optional<Users> findByEmail(String email);
}
