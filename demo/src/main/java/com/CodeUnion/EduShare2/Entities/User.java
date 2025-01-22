package com.CodeUnion.EduShare2.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "full_Name" , nullable = false)
	private String fullName;
	
	@Column(name = "university_mail" , nullable = false)
	private String universityMail;
	
	@Column(name = "password" , nullable = false)
	private String password;
	
	@Column(name = "googleId" , nullable = true)
	private String googleId;

}
