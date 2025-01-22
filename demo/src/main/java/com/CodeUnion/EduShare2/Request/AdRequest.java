package com.CodeUnion.EduShare2.Request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdRequest {

	private String title;
	private String faculty;
	private String description;
	private String method;
	private String price;
	private byte[] photo;
}
