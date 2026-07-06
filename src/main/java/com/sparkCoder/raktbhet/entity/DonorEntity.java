package com.sparkCoder.raktbhet.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@Table(name = "donor")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonorEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        @Column(name = "donor_id")
        private String donorId;


        private Integer raktbhetId;
        private String name;
        private Integer age;
        private String gender;
        private String bloodGrp;
        private String contact;
        private String email;
        private String password;

        @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        @JoinColumn(name = "address_id")
        private AddressEntity address;

    }


