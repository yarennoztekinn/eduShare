package com.CodeUnion.EduShare2.Verification;


import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.CodeUnion.EduShare2.Request.VerifyCodeRequest;

@Service
public class VerificationService {

    private final Map<String, String> verificationCodes = new HashMap<>();

    public String generateVerificationCode() { //Generate 6 digits random verification code
        return String.valueOf((int) ((Math.random() * 900000) + 100000));
    }
    

    public void saveVerificationCode(String email, String code) {
        verificationCodes.put(email, code);
    }
    

    public boolean verifyCode(String email, VerifyCodeRequest verifyCodeRequest) { //Verify the code
        return verificationCodes.containsKey(email) && verificationCodes.get(email).equals(verifyCodeRequest.getInputCode());
    }

    public void removeCode(String email) {
        verificationCodes.remove(email);
    }
}
