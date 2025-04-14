package com.theezy.data.models;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Document
@Data
public class User implements UserDetails {

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

    @Valid
    private Contact contact;


    private Role role;

    private List<Contact> contacts = new java.util.ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return contact.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}