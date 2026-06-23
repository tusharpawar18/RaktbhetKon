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
public class AddressDto {
    @NotNull(message="Id cannot be null")
    private Long id;

    @NotBlank(message="City cannot be blank")
    @Size(min=2,max=50, message="City must be between 2 and 50 characters")
    private String city;

    @NotBlank(message="State cannot be blank")
    @Size(min =2,max=50, message="State must be between 2 and 50 characters")
    private String state;

    @NotBlank(message="Country cannot be blank")
    @Size(min=2, max=50, message="Country must be between 2 and 50 characters")
    private String country;

    @NotBlank(message="LandMark cannot be blank")
    @Size(min=3, max=100, message="LandMark must be between 3 and 100 characters")
    private String landMark;

    @NotBlank(message="Pincode cannot be blank")
    @Size(min=6, max=6, message="Pincode must be 6 characters")
    private String pincode;
    }



