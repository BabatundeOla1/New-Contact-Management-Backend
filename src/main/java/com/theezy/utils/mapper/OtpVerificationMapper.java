package com.theezy.utils.mapper;

import com.theezy.data.models.OtpVerification;
import com.theezy.dto.requests.OtpSendRequest;
import com.theezy.dto.requests.OtpVerificationRequest;
import com.theezy.dto.responses.OtpVerificationResponse;

public class OtpVerificationMapper {

    public static OtpVerification mapRequestToOtpVerification(OtpVerificationRequest otpVerificationRequest){
        OtpVerification otpVerification = new OtpVerification();
        otpVerification.setCode(otpVerificationRequest.getOtp());
        otpVerification.setEmail(otpVerificationRequest.getEmail());
        otpVerification.setExpirationTime(otpVerificationRequest.getExpirationTime());
        return otpVerification;
    }

    public static OtpVerificationResponse mapMessageToResponse(String message){
        OtpVerificationResponse otpVerificationResponse = new OtpVerificationResponse();
        otpVerificationResponse.setMessage(message);
        return otpVerificationResponse;
    }
    public static OtpSendRequest mapToOtpSendRequest(String email){
        OtpSendRequest otpSendRequest = new OtpSendRequest();
        otpSendRequest.setEmail(email);
        return otpSendRequest;
    }
}
