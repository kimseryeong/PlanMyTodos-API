package com.todoweb.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoweb.api.common.auth.CustomUserDetails;
import com.todoweb.api.common.auth.dto.SessionUser;
import com.todoweb.api.common.status.Role;
import com.todoweb.api.domain.user.LoginType;
import com.todoweb.api.domain.user.UserRepository;
import com.todoweb.api.domain.user.Users;
import com.todoweb.api.dto.user.LoginRequestDTO;
import com.todoweb.api.dto.user.SignUpRequestDTO;
import com.todoweb.api.dto.user.UserResponseDTO;

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
		
		Users user = userRepository.findByEmail(dto.getEmail());
		
		if(user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
			return UserResponseDTO.builder()
					.email(null)
					.message("아이디와 비밀번호를 확인하세요.")
					.isError(true)
					.build();
		}
		
		httpSession.setAttribute("user", new SessionUser(user));
		
		UserDetails userDetails = new CustomUserDetails(user);
		
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, dto.getPassword(), userDetails.getAuthorities());
		context.setAuthentication(authentication);
		
		SecurityContextHolder.setContext(context);
        
		return UserResponseDTO.fromEntity(user, "로그인 되었습니다.", false);
		
		
	}
	
}
