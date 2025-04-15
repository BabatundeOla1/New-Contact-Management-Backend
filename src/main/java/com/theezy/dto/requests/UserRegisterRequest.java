package com.theezy.dto.requests;

import com.theezy.data.models.Contact;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
public class UserRegisterRequest {

    @Valid
    @Id
    private String id;

    @NotEmpty(message = "Name can not be empty")
    @NotNull(message = "FirstName is required")
    @NotBlank(message = "FirstName is require")
    private String firstName;

    @NotEmpty(message = "Name can not be empty")
    @NotNull(message = "lastName is required")
    @NotBlank(message = "lastName is require")
    private String lastName;

    @NotEmpty(message = "password can not be empty")
    @NotNull(message = "password is required")
    @NotBlank(message = "password is require")
    private String password;
    private boolean isVerified;

    @Valid
    private Contact contact;

    private List<Contact> contacts = new java.util.ArrayList<>();
}
