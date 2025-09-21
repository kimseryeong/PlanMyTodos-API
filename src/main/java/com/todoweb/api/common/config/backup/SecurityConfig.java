//package com.todoweb.api.common.config.backup;
//
//import java.util.Arrays;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import com.todoweb.api.common.auth.oauth.CustomOAuth2UserService;
//
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfig {
//
//	@Autowired
//	private CustomOAuth2UserService customOAuth2UserService;
//	
//	@Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }
//	
//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
//		http
//	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//	        .csrf(csrf -> csrf.disable())
//	        .authorizeHttpRequests(requests -> requests
//	        		.requestMatchers("/", "/user/**", "/oauth2/**").permitAll()
//		            .anyRequest().authenticated()
//            )
//	        .formLogin(form -> form
//	        		.loginPage("/")
//	        		.loginProcessingUrl("/login")
//	        		.defaultSuccessUrl("/")
//    		)
//	        .oauth2Login(oauth -> oauth
//	        		.userInfoEndpoint(userInfo -> userInfo
//	        				.userService(customOAuth2UserService)
//    				)
//	        		.defaultSuccessUrl("https://planmytodos.netlify.app", true)
//	        		
//    		)
//	        .logout(logout -> logout.logoutSuccessUrl("/user/successLogout"))
//	        .exceptionHandling(ex -> ex
//	                .authenticationEntryPoint((request, response, authException) -> {
//	                    
//	                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//	                })
//	            );
//		
//		
//	    return http.build();
//	}
//	
//	@Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        
//        configuration.setAllowedOrigins(List.of("https://planmytodos.netlify.app", "http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-XSRF-TOKEN"));
//        configuration.setAllowCredentials(true);
//        
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        
//        return source;
//    }
//}
