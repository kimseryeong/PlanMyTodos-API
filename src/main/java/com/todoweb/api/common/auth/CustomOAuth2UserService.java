package com.todoweb.api.common.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.todoweb.api.common.auth.dto.OAuthAttributes;
import com.todoweb.api.common.auth.dto.SessionUser;
import com.todoweb.api.common.status.Role;
import com.todoweb.api.domain.user.LoginType;
import com.todoweb.api.domain.user.UserRepository;
import com.todoweb.api.domain.user.Users;

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
		
        Users user = userRepository.findByEmail(userEmail)
        	    .orElseGet(() -> {
        	        Users newUser = Users.builder()
        	                .email(userEmail)
        	                .loginType(LoginType.GOOGLE)
        	                .role(Role.USER)
        	                .build();
        	        return userRepository.save(newUser);
        	    });
        
        httpSession.setAttribute("user", new SessionUser(user));
        
        return new CustomUserDetails(user, attributes.getAttributes());

	}
	
}
