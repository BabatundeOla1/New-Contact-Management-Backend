package com.theezy.services;

import com.theezy.dto.responses.CallerDetailsResponse;
import reactor.core.publisher.Mono;

public interface CallerDetailsVerificationService {
    Mono<CallerDetailsResponse> validatePhoneNumber(String phoneNumber);

}
