package com.sparkCoder.raktbhet.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonorResDto {

        private String donorId;
        private Integer raktbhetId;
        private String name;
        private Integer age;
        private String gender;
        private String bloodGrp;
        private String contact;
        private String email;
        private AddressDto address;



}
