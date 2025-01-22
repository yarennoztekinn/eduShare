package com.CodeUnion.EduShare2.Entities;



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
@NoArgsConstructor
@AllArgsConstructor
@Table(name="ads")
public class Ad {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne                                    
	@JoinColumn(name="user_id", nullable = true) 
	private User user;
	
	@Column(name="title", nullable = true)
	private String title;
	
	@Column(name="faculty",nullable = true)
	private String faculty;
	
	@Column(name="method",nullable = true)
	private String method;
	
	@Column(name="description",nullable = true)
	private String description;
	
	@Column(name="price",nullable = true)
	private String price;
	
	@Lob
	@Column(name="photo", nullable = true)
	private byte[] photo;
	
}