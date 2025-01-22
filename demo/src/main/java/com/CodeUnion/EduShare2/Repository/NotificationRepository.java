package com.CodeUnion.EduShare2.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CodeUnion.EduShare2.Entities.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long>{
	
	
	//@Query("SELECT n FROM Notification n WHERE n.userRespondId = :userRespondId")
	//List<Notification> findAllById(Long id);

	//List<Notification> findAllByUserId(Long id);
	
	@Query("SELECT n FROM Notification n WHERE n.respondUser.id = :userRespondId")
	List<Notification> findAllByRespondUserId(@Param("userRespondId") Long userRespondId);



}
