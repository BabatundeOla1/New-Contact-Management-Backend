package com.theezy.controller;

import com.theezy.dto.requests.OtpSendRequest;
import com.theezy.dto.requests.VerifyOtpCode;
import com.theezy.dto.responses.OtpVerificationResponse;
import com.theezy.services.OtpVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp/")
public class OtpVerificationController {

    @Autowired
    private OtpVerificationService otpVerificationService;

    @PostMapping("send")
    public ResponseEntity<OtpVerificationResponse> sendOtp(@RequestBody OtpSendRequest request) {
        otpVerificationService.sendOtp(request);
        return ResponseEntity.ok(new OtpVerificationResponse());
    }

    @PostMapping("verify-otp/{email}/verifyotp")
    public ResponseEntity<OtpVerificationResponse> verifyOtp(@PathVariable("email") String email, @RequestBody VerifyOtpCode verifyOtpCode) {
        return ResponseEntity.ok(otpVerificationService.verifyOtp(email, verifyOtpCode));
    }
}
