package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.DonorReqDto;
import com.sparkCoder.raktbhet.dto.DonorResDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface DonorService {

        DonorResDto createDonor(DonorReqDto createDTO);

        Optional<DonorResDto> findById(String donorId);

        Optional<DonorResDto> findByEmail(String email);

        Optional<DonorResDto> findByRaktbhetId(Integer raktbhetId);

        List<DonorResDto> findByBloodGrp(String bloodGrp);

        void deleteById(String donorId);

        Page<DonorResDto> getAllDonors(int page, int size);
}