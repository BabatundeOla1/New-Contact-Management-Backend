package com.theezy.data.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Data
public class Contact implements Serializable {
    @Valid
    @Id
    private String id;

    @NotEmpty(message = "Name can not be empty")
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name is require")
    private String name;

    @NotEmpty(message = "email can not be empty")
    @NotNull(message = "email is required")
    @NotBlank(message = "email is require")
    private String email;

    @NotEmpty(message = "phoneNumber can not be empty")
    @NotNull(message = "phoneNumber is required")
    @NotBlank(message = "phoneNumber is require")
    private String phoneNumber;

    @NotEmpty(message = "address can not be empty")
    @NotNull(message = "address is required")
    @NotBlank(message = "address is require")
    private String address;

    private boolean blocked;

    private String userId;
}
