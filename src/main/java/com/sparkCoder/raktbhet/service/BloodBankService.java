package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.BloodBankDto;
import org.springframework.http.ResponseEntity;

public interface BloodBankService {


        BloodBankDto save(BloodBankDto dto);

        BloodBankDto getById(Integer id);

        BloodBankDto update(Integer id, BloodBankDto dto);

        ResponseEntity<String> delete(Integer id);
    }


