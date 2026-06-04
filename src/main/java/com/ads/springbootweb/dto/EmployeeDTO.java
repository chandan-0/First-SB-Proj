package com.ads.springbootweb.dto;

import com.ads.springbootweb.anotation.EmployeeRoleValidation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private Long id;

    @NotBlank(message = "Required Field Employee")
    @Size(min = 3, max = 20, message = "The length of your name should be between 3 to 20 characters")
    private String name;

    @Email(message = "Email Should be an valid email")
    private String email;
    @NumberFormat
    @Min(value = 18, message = "Age Can not less then 18.")
    @Max(value = 60, message = "Age Can not grater then 60.")
    private Integer age;

    @Past(message = "Date Of joining should be past date")
    private LocalDate dateOfJoining;

    @NotBlank(message = "Role of the employee can not be blank data should be USER Or ADMIN")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "The Role of employee USER Or ADMIN")
    @EmployeeRoleValidation(message = "The Role of employee should be one of these USER Or ADMIN - CUSTOM -> EmployeeRoleValidation")
    private String role; // ADMIN Or USER

    @AssertTrue(message = "Employee Should be active")
    @JsonProperty("isActive")
    private Boolean isActive;
}
