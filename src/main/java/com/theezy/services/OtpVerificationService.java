package com.theezy.services;

import com.theezy.dto.requests.OtpSendRequest;
import com.theezy.dto.requests.OtpVerificationRequest;
import com.theezy.dto.requests.VerifyOtpCode;
import com.theezy.dto.responses.OtpVerificationResponse;
import com.theezy.dto.responses.UserRegisterResponse;

public interface OtpVerificationService {

    void sendOtp(OtpSendRequest request);
UserRegisterResponse verifyOtp(String email, VerifyOtpCode verifyOtpCode);
}
