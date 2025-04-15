package com.theezy.dto.requests;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RegisterRequest {

    @Valid
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
