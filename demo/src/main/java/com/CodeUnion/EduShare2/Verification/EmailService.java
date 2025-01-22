package com.CodeUnion.EduShare2.Verification;


import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.CodeUnion.EduShare2.Entities.User;
import com.CodeUnion.EduShare2.Repository.UserRepository;
import com.CodeUnion.EduShare2.Request.VerifyCodeRequest;

import jakarta.mail.MessagingException;
import jakarta.mail.Transport;
import jakarta.mail.internet.MimeMessage;



@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final VerificationService verificationService;
    private final UserRepository userRepository;

    public EmailService(JavaMailSender mailSender, VerificationService verificationService, UserRepository userRepository) {
        this.mailSender = mailSender;
        this.verificationService = verificationService;
        this.userRepository = userRepository;
    }
    
    public void sendVerificationEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("EduShare E-posta Doğrulama Kodu");
        message.setText("E-posta doğrulama kodunuz: " + code);

        try {
            mailSender.send(message); // Kod gönderimi
        } catch (MailSendException e) {
            throw new RuntimeException("Geçersiz bir e-posta adresi nedeniyle gönderim başarısız oldu: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("E-posta gönderimi sırasında bir hata oluştu: " + e.getMessage(), e);
        }
    }
    
    /*
    public void sendVerificationEmail(String toEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("EduShare E-posta Doğrulama Kodu");
        message.setText("E-posta doğrulama kodunuz: " + code);

        try {
            // Doğrudan JavaMail API'sini kullanarak Transport üzerinden gönderim
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(toEmail);
            helper.setSubject(message.getSubject());
            helper.setText(message.getText());
            
            // SMTP bağlantısını kontrol et ve hata yakala
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("E-posta gönderimi başarısız: " + e.getMessage(), e);
        }
    }
    */


    public boolean verifyEmail(User requestUser, VerifyCodeRequest verifyCodeRequest) {
      
        if (verificationService.verifyCode(requestUser.getUniversityMail(), verifyCodeRequest)) {  // Kod doğrulaması

            userRepository.save(requestUser);

            verificationService.removeCode(requestUser.getUniversityMail()); // Kullanılan kodu kaldır
            return true;
        }
        return false; // Kod geçersiz
    }

    public boolean isMuEmail(String email) {
        return email.endsWith("@posta.mu.edu.tr");
    }
}
