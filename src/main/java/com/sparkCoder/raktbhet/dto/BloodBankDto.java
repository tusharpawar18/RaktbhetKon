package com.sparkCoder.raktbhet.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Valid
public class BloodBankDto {


    @NotNull(message="Blood Bank Id cannot be null")
    private Integer bloodBankId;

    @NotBlank(message="Blood Bank Name cannot be blank")
    @Size(min=3,max=100, message="Blood Bank Name must be between 3 and 100 characters")
    private String bloodBankName;

    @NotBlank(message="License Number cannot be blank")
    @Size(min=5,max=30, message="License Number must be between 5 and 30 characters")
    private String licenseNumber;

    @NotBlank(message="Address cannot be blank")
    @Size(min=10,max=200, message="Address must be between 10 and 255 characters")
    private String address;

    @NotNull(message="Contact Number cannot be null")
    private Long contactNumber;

    }


