package com.project.carrental.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {

    private LocalDate startDate;
    private LocalDate endDate;

    @Size(max = 30, message = "First name is too long (max 30 characters)")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(max = 30, message = "Last name is too long (max 30 characters)")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email not valid")
    private String email;

    @Size(min = 9, max = 9, message = "Phone number not valid")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;
}
