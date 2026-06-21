package com.sparkCoder.raktbhet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



    @Entity
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public class BankAdminEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer userId;

        private String name;
        private String age;
        private String gender;
        private Long contactNumber;
        private Integer bankId;
        private String identityNumber;
        private String userName;
        private String password;


    }


