package com.railway.userservice.dto;

import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.annotations.processing.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long id;

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 50, message = "Username must be between 4 and 50 characters")
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @NotBlank(message = "Address is required")
    @Size(max = 200, message = "Address must be at most 200 characters")
    private String address;

    @URL(message = "Invalid profile image URL")
    private String profileImageUrl;

    @NotNull(message = "Roles cannot be null")
    @Size(min = 1, message = "At least one role is required")
    private Set<String> roles;

    @NotNull(message = "User active status is required")
    private Boolean isActive;

    @NotNull(message = "Email verification status is required")
    private Boolean isEmailVerified;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
