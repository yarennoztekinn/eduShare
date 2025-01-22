package com.CodeUnion.EduShare2.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogInRequest {
	
	private String universityMail;
	private String password;

}
