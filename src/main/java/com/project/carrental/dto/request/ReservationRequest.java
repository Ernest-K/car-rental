package com.project.carrental.dto.request;

import jakarta.validation.constraints.*;
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

    @FutureOrPresent(message = "Start date is not valid")
    private LocalDate startDate;

    @FutureOrPresent(message = "End date is not valid")
    private LocalDate endDate;

    @Size(max = 30, message = "First name is too long (max 30 characters)")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(max = 30, message = "Last name is too long (max 30 characters)")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email not valid")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 9, max = 9, message = "Phone number not valid")
    @NotBlank(message = "Phone number is required")
    private String phoneNumber;

    @AssertTrue(message = "End date must be after or equal to start date")
    public boolean isEndDateValid() {
        return startDate == null || endDate == null || !endDate.isBefore(startDate);
    }
}
