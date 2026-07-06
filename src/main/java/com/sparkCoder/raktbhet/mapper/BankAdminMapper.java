package com.sparkCoder.raktbhet.mapper;

import com.sparkCoder.raktbhet.dto.BankAdminDto;
import com.sparkCoder.raktbhet.entity.BankAdminEntity;

public class BankAdminMapper {


        public static BankAdminEntity dtoToEntity(BankAdminDto dto) {

            BankAdminEntity admin = new BankAdminEntity();

            admin.setUserId(dto.getUserId());
            admin.setName(dto.getName());
            admin.setAge(dto.getAge());
            admin.setGender(dto.getGender());
            admin.setContactNumber(dto.getContactNumber());
            admin.setBankId(dto.getBankId());
            admin.setIdentityNumber(dto.getIdentityNumber());
            admin.setUserName(dto.getUserName());
            admin.setPassword(dto.getPassword());

            return admin;
        }

        public static BankAdminDto entityToDto(BankAdminEntity entity) {

            BankAdminDto dto = new BankAdminDto();

            dto.setUserId(entity.getUserId());
            dto.setName(entity.getName());
            dto.setAge(entity.getAge());
            dto.setGender(entity.getGender());
            dto.setContactNumber(entity.getContactNumber());
            dto.setBankId(entity.getBankId());
            dto.setIdentityNumber(entity.getIdentityNumber());
            dto.setUserName(entity.getUserName());
            dto.setPassword(entity.getPassword());

            return dto;
        }
    }

