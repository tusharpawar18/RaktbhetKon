package com.sparkCoder.raktbhet.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Valid
public class DonorReqDto {

    @NotBlank(message="Name cannot be blank")
    @Size(min=3, max=50, message="Name must be between 3 and 50 characters")
    private String name;

    @NotNull(message="Age cannot be null")
    private Integer age;

    @NotBlank(message="Gender cannot be blank")
    private String gender;

    @NotBlank(message="Blood Group cannot be blank")
    private String bloodGrp;

    @NotBlank(message="Contact cannot be blank")
    @Size(min=10, max=10, message="Contact must be 10 digits")
    private String contact;

    @NotBlank(message="Email cannot be blank")
    @Size(min=5, max=100, message="Email must be between 5 and 100 characters")
    private String email;

    @NotBlank(message="Password cannot be blank")
    @Size(min=8, max=20, message="Password must be between 8 and 20 characters")
    private String password;


    @NotNull(message="Address cannot be null")
    private AddressDto address;
    }



