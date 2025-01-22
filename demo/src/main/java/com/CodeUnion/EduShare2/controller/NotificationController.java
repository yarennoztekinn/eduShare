package com.CodeUnion.EduShare2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.CodeUnion.EduShare2.Entities.Ad;
import com.CodeUnion.EduShare2.Entities.Notification;
import com.CodeUnion.EduShare2.Service.AdService;
import com.CodeUnion.EduShare2.Service.NotificationService;

import jakarta.servlet.http.HttpSession;

@RestController
public class NotificationController {
	
	@Autowired
	NotificationService notificationService;
	
	@Autowired
	AdService adService;
	

	@GetMapping(path = "/my-notifications", produces="application/json")
	public ResponseEntity<?> getAllNotificationsByLoggedUserId(HttpSession session) {
	    List<Notification> notifications = notificationService.getAllNotificationsByLoggedUserId(session);
	    
	    if (notifications.isEmpty()) {
	        return ResponseEntity.ok().body(Map.of("message", "Henüz bir bildiriminiz yok"));
	    }

	    return ResponseEntity.ok(notifications);
	}

	
	
	//view butonu ilanin idsiyle buraya gelicek
	
	@GetMapping(path = "/send-notification/{adId}")
	public ResponseEntity<?> createNotification(@PathVariable("adId") Long adId, HttpSession session) {
		Ad ad = adService.getAdDetailsByAdId(adId);
		if (notificationService.createNotification(ad,session)) {
			return ResponseEntity.ok(ad);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ilan bulunamadi!");
    }

}
