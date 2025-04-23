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
	
	private HttpSession httpSession;
	
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
    		
        if(!userRepository.existsByEmail(userEmail)) {
        	Users user = Users.builder()
	        				.email(userEmail)
	        				.loginType(LoginType.GOOGLE)
	        				.role(Role.USER)
	        				.build();
        	
        	userRepository.save(user);
        }
        else {
        	userRepository.findByEmail(userEmail);
        	
        }
        
        //httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());

	}
	
//	@Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        // 여기에 구글 사용자 정보 받아서 User 객체로 매핑하는 로직 작성
//        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
//
//        // 사용자 정보 꺼내기
//        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google
//        String userNameAttributeName = userRequest.getClientRegistration()
//            .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
//
//        Map<String, Object> attributes = oAuth2User.getAttributes();
//
//        // 여기서 email 등 필요한 정보 추출해서 회원가입 or 로그인 처리
//
//        return new DefaultOAuth2User(
//            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
//            attributes,
//            userNameAttributeName
//        );
//    }
}
