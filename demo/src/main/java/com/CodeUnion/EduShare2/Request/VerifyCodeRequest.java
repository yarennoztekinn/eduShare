package com.CodeUnion.EduShare2.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class VerifyCodeRequest {
	private String inputCode;
}
