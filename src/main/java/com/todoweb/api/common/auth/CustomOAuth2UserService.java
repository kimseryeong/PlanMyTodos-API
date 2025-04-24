package com.todoweb.api.common.auth;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.todoweb.api.common.auth.dto.OAuthAttributes;
import com.todoweb.api.common.auth.dto.SessionUser;
import com.todoweb.api.common.status.Role;
import com.todoweb.api.domain.user.LoginType;
import com.todoweb.api.domain.user.UserRepository;
import com.todoweb.api.domain.user.Users;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{

	@Autowired
    private final UserRepository userRepository;
	
	@Autowired
	private final HttpSession httpSession;
	
	//private final HttpServletResponse response;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);
        
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName();
        
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        
        String userEmail = attributes.getEmail();
		log.info("userEmail: {}", userEmail);
		log.info("exitsByEmail: {}", userRepository.existsByEmail(userEmail));
    	
		Users user = null;
		
        if(!userRepository.existsByEmail(userEmail)) {
        	user = Users.builder()
	        				.email(userEmail)
	        				.loginType(LoginType.GOOGLE)
	        				.role(Role.USER)
	        				.build();
        	
        	userRepository.save(user);
        }
        else {
        	user = userRepository.findByEmail(userEmail);
        }
        
        log.info("DB ---> user: {}", user);
        
        httpSession.setAttribute("user", new SessionUser(user));

//        Cookie sessionCookie = new Cookie("JSESSIONID", httpSession.getId());
//        
//        sessionCookie.setHttpOnly(true);
//        sessionCookie.setSecure(true);
//        sessionCookie.setPath("/");
//        sessionCookie.setDomain("https://planmytodos-api-production.up.railway.app");
//        sessionCookie.setAttribute("SameSite", "None");
//        
//        response.addCookie(sessionCookie);
        
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

	}
	
}
