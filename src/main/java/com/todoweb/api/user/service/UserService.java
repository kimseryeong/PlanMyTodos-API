package com.todoweb.api.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoweb.api.common.auth.Role;
import com.todoweb.api.common.auth.user.SessionUser;
import com.todoweb.api.user.dto.LoginRequestDTO;
import com.todoweb.api.user.dto.SignUpRequestDTO;
import com.todoweb.api.user.dto.UserResponseDTO;
import com.todoweb.api.user.entity.LoginType;
import com.todoweb.api.user.entity.Users;
import com.todoweb.api.user.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	@Autowired
    private final UserRepository userRepository;

	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Autowired
	private HttpSession httpSession;
	
	/**
	 * 사용자 여부
	 * @param email
	 * @return
	 */
	public boolean chkDuplatedId(String email) {
		return userRepository.existsByEmail(email);
	}
	
	
	/**
	 * 사용자 등록
	 * @param dto
	 */
	@Transactional
	public UserResponseDTO signUp(SignUpRequestDTO dto) {
		
		log.info("UserService signup ... getPasswordCheck: {}", dto.getPasswordCheck());
		log.info("UserService signup ... getPassword: {}", dto.getPassword());
		
		if(userRepository.existsByEmail(dto.getEmail())) {
			log.info("이미 등록된 이메일입니다");
			
			return UserResponseDTO.builder()
					.email(null)
					.message("이미 등록된 이메일입니다")
					.isError(true)
					.build();
		}
		
		if(!dto.getPassword().equals(dto.getPasswordCheck())) {
			log.info("비밀번호가 일치하지 않습니다");

			return UserResponseDTO.builder()
					.email(null)
					.message("비밀번호가 일치하지 않습니다")
					.isError(true)
					.build();
		}
		
		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		
		Users entity = Users.builder()
				.email(dto.getEmail())
				.password(encodedPassword)
				.loginType(LoginType.LOCAL)
				.role(Role.USER)
				.build();
		
		Users savedEntity = userRepository.save(entity);
		
		UserResponseDTO result = UserResponseDTO.fromEntity(savedEntity, "등록되었습니다.", false);
		
		return result;
	}
	
	@Transactional
	public UserResponseDTO login(LoginRequestDTO dto) {
		
		Users user = userRepository.findByEmail(dto.getEmail())
			    .orElseThrow(() -> new UsernameNotFoundException("아이디와 비밀번호를 확인하세요."));

		
		if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return UserResponseDTO.builder()
					.email(null)
					.message("아이디와 비밀번호를 확인하세요.")
					.isError(true)
					.build();
		}
		
		return UserResponseDTO.fromEntity(user, "로그인 되었습니다.", false);
	}
	
}
