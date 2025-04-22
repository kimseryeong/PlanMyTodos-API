package com.todoweb.api.common.auth.dto;

import java.util.Map;

import com.todoweb.api.domain.user.LoginType;
import com.todoweb.api.domain.user.Users;

import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuthAttributes {

	private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String email;
    
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String email) {
    	this.attributes = attributes;
    	this.nameAttributeKey = nameAttributeKey;
    	this.email = email;
    }
    
    public static OAuthAttributes of(String registrationId,
	            String userNameAttributeName,
	            Map<String, Object> attributes) {
	
    	return ofGoogle(userNameAttributeName, attributes);
	}
    
    private static OAuthAttributes ofGoogle(String usernameAttributeName,
            Map<String, Object> attributes) {
    	
		return OAuthAttributes.builder()
			.email((String) attributes.get("email"))
			.attributes(attributes)
			.nameAttributeKey(usernameAttributeName)
			.build();
	}
    
    public Users toEntity() {
    	return Users.builder()
    			.email(email)
    			.loginType(LoginType.GOOGLE)
    			.build();
    }
}
