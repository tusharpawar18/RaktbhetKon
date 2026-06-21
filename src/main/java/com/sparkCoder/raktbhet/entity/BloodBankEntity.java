package com.sparkCoder.raktbhet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BloodBankEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bloodBankId;

    private String bloodBankName;
    private String licenseNumber;
    private String address;
    private Long contactNumber;


}
