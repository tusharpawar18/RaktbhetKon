package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.DonorReqDto;
import com.sparkCoder.raktbhet.dto.DonorResDto;

import java.util.List;
import java.util.Optional;
/**
 * Service API for donor operations.
 */
public interface DonorService {

        DonorResDto createDonor(DonorReqDto createDTO);

        Optional<DonorResDto> findById(String donorId);

        Optional<DonorResDto> findByEmail(String email);

        Optional<DonorResDto> findByRaktbhetId(Integer raktbhetId);

        List<DonorResDto> findByBloodGrp(String bloodGrp);

        void deleteById(String donorId);


        List<DonorResDto> findByLocation(String city, String state, String pincode);

    }



