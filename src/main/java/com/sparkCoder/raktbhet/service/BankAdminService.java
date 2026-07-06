package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.BankAdminDto;
import org.springframework.stereotype.Service;

@Service
public interface BankAdminService {

        BankAdminDto register(BankAdminDto dto);

        BankAdminDto getById(Integer id);

        String update(Integer id, BankAdminDto dto);

        String delete(Integer id);
    }


