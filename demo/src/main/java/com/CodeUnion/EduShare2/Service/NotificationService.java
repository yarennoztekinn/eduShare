package com.CodeUnion.EduShare2.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CodeUnion.EduShare2.Entities.Ad;
import com.CodeUnion.EduShare2.Entities.Notification;
import com.CodeUnion.EduShare2.Entities.User;
import com.CodeUnion.EduShare2.Repository.NotificationRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

@Service
public class NotificationService {
	
	@Autowired
	NotificationRepository notificationRepository;

	@Transactional
	public List<Notification> getAllNotificationsByLoggedUserId(HttpSession session) {
	    User loggedUser = (User) session.getAttribute("loggedUser");
	    if (loggedUser == null) {
	        return null;
	    }

	    // Burada, loggedUser.getId()'yi user_respond_id olarak kullanıyoruz
	    List<Notification> notifications = notificationRepository.findAllByRespondUserId(loggedUser.getId());
	    return notifications;
	}

	
	public boolean createNotification(Ad ad, HttpSession session) {
		if (ad == null) {
			return false;
		}
		
		User requestUser = (User) session.getAttribute("loggedUser");
		User respondUser = ad.getUser();
		
		String message = String.format(
				"%s adlı kullanıcı, '%s' başlıklı ilanınız ile ilgileniyor. (Tarih: %s)",
				requestUser.getFullName(),
				ad.getTitle(),
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
			);
		 
		Notification notification = new Notification();
		notification.setText(message);
		notification.setRequestUser(requestUser); // requestUser istek yollayan loggedUser
		notification.setRespondUser(respondUser); // ilan sahibi ilani karsilayan respondUser
		notification.setAd(ad);
		String status = "unread";
		notification.setStatus(status);
		notificationRepository.save(notification);
		return true;
	}

}
