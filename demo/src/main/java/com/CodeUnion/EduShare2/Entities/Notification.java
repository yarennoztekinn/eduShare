package com.CodeUnion.EduShare2.Entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="notifications")
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user_request_id",nullable = false)
	private User requestUser;
	
	@ManyToOne
	@JoinColumn(name = "user_respond_id",nullable = false)
	private User respondUser;
	
	@ManyToOne
	@JoinColumn(name = "ad_id",nullable = false)
	private Ad ad;
	
	@Column(name = "status")
	private String status;

	@Lob
	@Column(name= "text")
	private String text;
	
	
	

}
