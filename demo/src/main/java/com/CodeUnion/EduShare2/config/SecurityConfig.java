package com.CodeUnion.EduShare2.config;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import com.CodeUnion.EduShare2.Entities.User;
import com.CodeUnion.EduShare2.Service.UserService;

@Configuration
public class SecurityConfig {
	
	@Autowired
	UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırak
            .authorizeHttpRequests(authz -> authz
                .requestMatchers(
                        "/start",
                        "/home",
                        "/create-ad",
                        "/show-ads",
                        "/ad-details",
                        "/forum",
                        "/notifications",
                        "/afterGoogle",
                        
                        "/ad/create",
                        "/ad/getAdByFacultyName/{faculty}",
                        "/ad/getDetails/{Id}",
                        "/ad/my-ads",
                        "/ad/{Id}/owner",
                        "/ad/delete",
                        "/send-notifications/{adId}",
                        "/user/verify",
                        "/user/signup", 
                        "/user/login", 
                        "/user/search/{googleId}",
                        "/user/setSession",
                        "/my-notifications",
                        "/send-notification/{adId}",
                        "/logout",
                        "/my-ads",
                        "/ad/ilanlarim",
           
                        "/css/**",
                        "/js/**",
                        "/images/**"
                    
                ).permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(oauth2 -> oauth2
            		.loginPage("/start")
            	    .successHandler((request, response, authentication) -> {
            	        // Google'dan dönen kimlik doğrulama detaylarını al
            	        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
            	        Map<String, Object> attributes = token.getPrincipal().getAttributes();

            	        String googleId = attributes.get("sub").toString();
            	        String fullName = attributes.get("name").toString();
            	        
            	        System.out.println(fullName);//kontrol amacli
            	        
            	        Optional<User> userOptional = userService.searchByFullName(fullName);//daha onceden normal kayit var diyelim
            	        if (!userOptional.isEmpty()) {//sistemde fullname buldu
							User user = userOptional.get();
							if (user.getGoogleId() == null) {//googleid bos mu bakti
								user.setGoogleId(googleId);// bossa google id sini doldurduk
								userService.save(user);
							}
							
						}
            	        String redirectUrl = "http://localhost:8080/afterGoogle?googleId=" + googleId;
            	        response.sendRedirect(redirectUrl);
            	    })
            	)
            .logout(logout -> logout
                .logoutUrl("/user/logout")
                .logoutSuccessUrl("/start")
                .invalidateHttpSession(true)
            );
        return http.build();
    }
}