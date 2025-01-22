package com.CodeUnion.EduShare2.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CodeUnion.EduShare2.Entities.User;
import com.CodeUnion.EduShare2.Request.LogInRequest;
import com.CodeUnion.EduShare2.Request.SignUpRequest;
import com.CodeUnion.EduShare2.Request.VerifyCodeRequest;
import com.CodeUnion.EduShare2.Service.UserService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping(path = "/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	

	@PostMapping(path = "/signup")
	public ResponseEntity<?> signUP(@RequestBody SignUpRequest signUpRequest) {
		return userService.signUP(signUpRequest);
	}
	
	@PostMapping(path = "/login")
	public ResponseEntity<?> logIN(@RequestBody LogInRequest logInRequest, HttpSession session) {
	    boolean isAuthenticated = userService.logIN(logInRequest,session);
	    if (isAuthenticated) {
	        return ResponseEntity.ok().body(Map.of("message", "giriş başarılı"));
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Hatalı mail ya da şifre"));
	    }
	}
	
	@GetMapping("/logout")
    public ResponseEntity<String> logOUT(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Çıkış başarılı!");
    }
	
	@PostMapping(path = "/verify")
	public ResponseEntity<?>  verifyEPosta(@RequestBody VerifyCodeRequest verifyCodeRequest){
		if (verifyCodeRequest == null) {
			return ResponseEntity.ok().body(Map.of("message", "Lütfen kodu giriniz"));
		}
		boolean isEntered = userService.sendToVerifyEmail(verifyCodeRequest);
		if (isEntered) {
			return ResponseEntity.ok().body(Map.of("message", "Hesabınız doğrulandı"));
		}
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "yanlış doğrulama kodu girdiniz"));

	}
	
	@GetMapping(path = "/account") //user panelinde hesap butonu
	public User account(HttpSession session) {
		return userService.account(session);
	}
	
	
	public Optional<User> getUserById(long Id) {
		return userService.getUserById(Id);
	}
	
	
	@GetMapping(path="/search/{googleId}", produces="application/json")
	public ResponseEntity<?> searchGoogleId(@PathVariable("googleId") String googleId){
		Optional<User>  user = userService.searchGoogleId(googleId);
		if (user.isPresent()) {
			return ResponseEntity.ok().body(Map.of("exists", true, "message", "kayitli"));

		}
		//googledan donen full name ile de search yapmamiz gerek
		return ResponseEntity.ok().body(Map.of("exists", false, "message", "kayit yok"));
	}
	
	
	public Optional<User> searchByFullName(String fullName){
		return userService.searchByFullName(fullName);
	}
	
	
	@PostMapping(path = "/setSession")
	public ResponseEntity<?> setSession(@RequestBody Map<String, String> requestData, HttpSession session) {
	    String googleId = requestData.get("googleId");
	    Optional<User> userOptional = userService.searchGoogleId(googleId);

	    if (userOptional.isPresent()) {
	        User user = userOptional.get();
	        session.setAttribute("loggedUser", user);
	        return ResponseEntity.ok(Map.of("message", "Session created successfully."));
	    } else {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "User not found."));
	    }
	}

	
}
