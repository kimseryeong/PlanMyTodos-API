package com.todoweb.api.common.auth.oauth;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.todoweb.api.user.entity.Users;

public class CustomUserDetails implements UserDetails, OAuth2User {

	private Users users;
	
	private Map<String, Object> attributes;
	
	public CustomUserDetails(Users users) {
		this.users = users;
		this.attributes = null;
	}

	//google login
	public CustomUserDetails(Users users, Map<String, Object> attributes) {
        this.users = users;
        this.attributes = attributes;
    }
	
	public Users getUser() {
        return users;
    }
	
	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + users.getRole().name()));
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getEmail();
    }
    
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    
    @Override
    public Map<String, Object> getAttributes() {
        return attributes != null ? attributes : new HashMap<>();
    }

    @Override
    public String getName() {
        return users.getEmail();
    }
}
