package com.theezy.services;

import com.theezy.dto.requests.OtpSendRequest;
import com.theezy.dto.requests.OtpVerificationRequest;
import com.theezy.dto.requests.VerifyOtpCode;
import com.theezy.dto.responses.OtpVerificationResponse;

public interface OtpVerificationService {

    void sendOtp(OtpSendRequest request);
//    OtpVerificationResponse verifyOtp(OtpVerificationRequest request);
OtpVerificationResponse verifyOtp(String email, VerifyOtpCode verifyOtpCode);
}
