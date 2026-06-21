package com.sparkCoder.raktbhet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorReqDto {

        private String name;
        private Integer age;
        private String gender;
        private String bloodGrp;
        private String contact;
        private String email;
        private String password;
        private AddressDto address;
    }



