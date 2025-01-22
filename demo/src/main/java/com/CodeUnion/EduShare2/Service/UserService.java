package com.CodeUnion.EduShare2.Service;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CodeUnion.EduShare2.Entities.User;
import com.CodeUnion.EduShare2.Repository.UserRepository;
import com.CodeUnion.EduShare2.Request.LogInRequest;
import com.CodeUnion.EduShare2.Request.SignUpRequest;
import com.CodeUnion.EduShare2.Request.VerifyCodeRequest;
import com.CodeUnion.EduShare2.Verification.EmailService;
import com.CodeUnion.EduShare2.Verification.VerificationService;

import jakarta.servlet.http.HttpSession;




@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
    EmailService emailService;
	
	@Autowired
    VerificationService verificationService;

	private User requestUser;
	
	public ResponseEntity<?> signUP(SignUpRequest signUpRequest) {
		   
	    Optional<User> existingUser = userRepository.findByUniversityMail(signUpRequest.getUniversityMail());
	    if (existingUser.isPresent()) { // E-posta kontrolü: Daha önce bu e-posta ile kullanıcı var mı?
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "zaten kayitli kullanici"));
	    }
	    
	    if (!emailService.isMuEmail(signUpRequest.getUniversityMail())) {// Geçerli e-posta mı?
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "geçersiz e-mail"));
	    }

	    User user = new User();
	    BeanUtils.copyProperties(signUpRequest, user);
	    requestUser = user;
	    
	    try {
	        if (sendVerificationCode(user.getUniversityMail())) { // Doğrulama kodu gönder
	            return ResponseEntity.ok().body(Map.of("message", "kod basarili bir sekilde gonderildi"));
	        } else {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "verification-code-failed"));
	        }
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "error-saving-user"));
	    }
	    
	}

	
	public boolean logIN(LogInRequest logInRequest, HttpSession session) {
		Optional<User> user = userRepository.findByUniversityMail(logInRequest.getUniversityMail());
		if (user.isEmpty()) {
			return false;
		}else {
			if (logInRequest.getPassword().equals(user.get().getPassword()) ) {
				session.setAttribute("loggedUser", user.get());
				return true;
			}
			return false;
		}	
	}

	public boolean sendToVerifyEmail(VerifyCodeRequest verifyCodeRequest) {
		return emailService.verifyEmail(requestUser, verifyCodeRequest);

	}

	/*
	public boolean sendVerificationCode(String toEmail) {
		String verificationCode = verificationService.generateVerificationCode();
		if (toEmail != null) {
			emailService.sendVerificationEmail(toEmail, verificationCode);
		    verificationService.saveVerificationCode(toEmail, verificationCode);
		    return true;
		}
		return false;
	}
	*/
	
	
	public boolean sendVerificationCode(String toEmail) {
	    String verificationCode = verificationService.generateVerificationCode();//Eğer geçerli e-posta ve kayıtlıysa, doğrulama kodu gönder
	    
	    try {
	        
	        emailService.sendVerificationEmail(toEmail, verificationCode);// Kod gönderme ve kaydetme işlemleri
	        verificationService.saveVerificationCode(toEmail, verificationCode);
	        return true; // Kod başarıyla gönderildi
	    } catch (Exception e) {
	        
	        return false;// Hata durumunda
	    }
	}


	public Optional<User> getUserById(long id) {
		 return userRepository.findById(id);

	}

	public User account(HttpSession session) {
		User user = (User)session.getAttribute("loggedUser");
		return user;
	}
	
	
	public Optional<User> searchGoogleId(String googleId) {
		return userRepository.findByGoogleId(googleId);
	}

	public Optional<User> searchByFullName(String fullName) {
		return userRepository.findByFullName(fullName);
	}

	public void save(User user) {
		userRepository.save(user);
	}

}
