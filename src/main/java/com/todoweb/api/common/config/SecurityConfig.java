package com.todoweb.api.common.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.todoweb.api.common.auth.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .authorizeHttpRequests(requests -> requests
        		.requestMatchers("/", "/user/**", "/oauth2/**").permitAll()
	            .anyRequest().permitAll())
	        .oauth2Login(Customizer.withDefaults()); // 사용자 정의 로그인 페이지 경로 (선택 사항)
		
	    return http.build();
//		http
//			.csrf(csrf -> csrf.disable())
//			.httpBasic(httpBasic -> httpBasic.disable())
//			.formLogin(form -> form.disable())
//	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
//	        .authorizeHttpRequests(auth -> auth
//	        		.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
//        		//.requestMatchers("/", "/user/**", "/oauth2/**").permitAll()
//				.anyRequest().authenticated()
//			)
//			.formLogin(form -> form
//                .loginPage("/login")
//                .defaultSuccessUrl("/")
//                .permitAll()
//            )
//			.oauth2Login(Customizer.withDefaults())
//			.logout(logout -> logout
//                .logoutSuccessUrl("/")
//                .permitAll()
//            );
//			
//		return http.build();
	}
	
	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.setAllowedOrigins(List.of("https://your-netlify-app-name.netlify.app", "http://localhost:3000")); // Netlify 앱 URL
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true); // 쿠키 및 인증 정보 공유 허용
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 CORS 설정 적용
        
        return source;
    }
}
