package com.theezy.services;

import com.theezy.data.models.JWTService;
import com.theezy.data.models.OtpVerification;
import com.theezy.data.models.User;
import com.theezy.data.repositories.OtpVerificationRepository;
import com.theezy.data.repositories.UserRepository;
import com.theezy.dto.requests.OtpSendRequest;
import com.theezy.dto.requests.OtpVerificationRequest;
import com.theezy.dto.requests.VerifyOtpCode;
import com.theezy.dto.responses.OtpVerificationResponse;
import com.theezy.dto.responses.UserRegisterResponse;
import com.theezy.utils.exception.InvalidOtpException;
import com.theezy.utils.exception.OtpExpiredException;
import com.theezy.utils.exception.OtpNotFoundException;
import com.theezy.utils.mapper.OtpVerificationMapper;
import com.theezy.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class OtpVerificationServiceImpl implements OtpVerificationService{

    @Autowired
    private OtpVerificationRepository otpVerificationRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private  JWTService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void sendOtp(OtpSendRequest request) {
        OtpVerificationRequest verificationRequest = new OtpVerificationRequest();

        String otpCode = generateOtp();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(20);

        verificationRequest.setEmail(request.getEmail());
        verificationRequest.setOtp(otpCode);
        verificationRequest.setExpirationTime(expirationTime);

        otpVerificationRepository.deleteByEmail(request.getEmail());

        OtpVerification verification = OtpVerificationMapper.mapRequestToOtpVerification(verificationRequest);
        otpVerificationRepository.save(verification);

        sendEmail(request.getEmail(), otpCode);
    }

    @Override
    public UserRegisterResponse verifyOtp(String email, VerifyOtpCode verifyOtpCode) {
        OtpVerification otpRecord = otpVerificationRepository.findByEmail(email)
                .orElseThrow(() -> new OtpNotFoundException("No OTP found for this email"));

        if (!otpRecord.getCode().equals(verifyOtpCode.getOtpCode())) {
            throw new InvalidOtpException("OTP is incorrect");
        }

        if (otpRecord.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new OtpExpiredException("OTP has expired");
        }

        User user = userRepository.findUserByContact_Email(email)
                .orElseThrow(() -> new RuntimeException("User not found for this email"));

        user.setVerified(true);
        userRepository.save(user);
        sendMessageToEmail(user.getContact().getEmail(), "Your Account has been verified");
        String jwtToken = jwtService.generateToken(user);
        otpVerificationRepository.deleteByEmail(email);
        return UserMapper.mapUserToResponse(jwtToken, user, "Email Verified Successfully");
//         OtpVerificationMapper.mapMessageToResponse("OTP verified successfully.");
    }


    private String generateOtp() {
        return String.valueOf(new Random().nextInt(999999 - 100000) + 100000);
    }

    private void sendEmail(String to, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Your OTP Code");
        message.setText("Your OTP is: " + otp);
        message.setFrom("verifynewuser11@gmail.com");
        javaMailSender.send(message);
    }
    private void sendMessageToEmail(String to, String verificationMessage) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("ACCOUNT VERIFICATION");
        message.setText(verificationMessage);
        message.setFrom("verifynewuser11@gmail.com");
        javaMailSender.send(message);
    }

}
