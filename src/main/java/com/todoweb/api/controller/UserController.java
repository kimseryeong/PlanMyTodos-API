package com.todoweb.api.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todoweb.api.common.auth.dto.SessionUser;
import com.todoweb.api.dto.user.LoginRequestDTO;
import com.todoweb.api.dto.user.SignUpRequestDTO;
import com.todoweb.api.dto.user.UserResponseDTO;
import com.todoweb.api.service.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController<T> {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	
	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO dto){
		try {
			UserResponseDTO response = userService.signUp(dto);
			log.info("signup ... response: {}", response);
			
			return ResponseEntity.ok().body(response);
		}
		catch(RuntimeException e) {
			log.error("signup ... exception: {}", e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
		}
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto){
		try {
			UserResponseDTO response = userService.login(dto);
			return ResponseEntity.ok().body(response);
		}
		catch(RuntimeException e) {
			log.error("signup ... exception: {}", e.getMessage());
			
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청");
		}
	}
	
	@GetMapping("/loginSuccess")
    public ResponseEntity<?> loginSuccess(@AuthenticationPrincipal OAuth2User oauth2User) {
        System.out.println("소셜 로그인 성공: " + oauth2User.getName());
        
        
        log.info("email: {}", oauth2User.getAttribute("email").toString());
        
        return ResponseEntity.ok().body(oauth2User.getAttribute("email").toString());
    }
	
	@GetMapping("/me")
	public ResponseEntity<SessionUser> getUserSession(){
		
		SessionUser user = (SessionUser) httpSession.getAttribute("user");
		
		if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		
        return ResponseEntity.ok(user); 
	}
	
	@GetMapping("/successLogout")
	public ResponseEntity<?> successLogout(){
		log.info("successLogout");
		
		Map<String, Object> response = new HashMap();
		
		response.put("success", true);
		response.put("message", "로그아웃 되었습니다.");
		
		return ResponseEntity.ok(response);
	}

}
