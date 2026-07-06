package com.sparkCoder.raktbhet.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Valid
public class BankAdminDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

        @NotBlank(message = "Name cannot be blank")
        @Size(min=3, max=50, message = "Name must be between 3 and 50 characters")
        private String name;


        @NotBlank(message="Age cannot be blank")
         private String age;

        @NotBlank(message="Gender cannot be blank")
        private String gender;

         @NotNull(message="Contact Number cannot be null")
         private Long contactNumber;

         @NotNull(message="Bank Id cannot be null")
         private Integer bankId;

         @NotBlank(message="Identity Number cannot be blank")
          @Size(min=5, max=20, message="Identity Number size must be between 5 and 20")
          private String identityNumber;

         @NotBlank(message="Username cannot be blank")
         @Size(min=4, max=20, message="Username size must be between 4 and 20")
          private String userName;

          @NotBlank(message="Password cannot be blank")
          @Size(min=8, max=20, message="Password size must be between 8 and 20")
           private String password;


    }

