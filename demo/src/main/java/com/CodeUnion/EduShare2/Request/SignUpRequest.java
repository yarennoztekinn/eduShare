package com.CodeUnion.EduShare2.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
	
	private String fullName;
	private String universityMail;
	private String password;
	private String googleId;

}
