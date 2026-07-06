package com.sparkCoder.raktbhet.mapper;

import com.sparkCoder.raktbhet.dto.BloodBankDto;
import com.sparkCoder.raktbhet.entity.BloodBankEntity;

public class BloodBankMapper {

        public static BloodBankEntity dtoToEntity(BloodBankDto dto) {

            BloodBankEntity entity = new BloodBankEntity();

            entity.setBloodBankId(dto.getBloodBankId());
            entity.setBloodBankName(dto.getBloodBankName());
            entity.setLicenseNumber(dto.getLicenseNumber());
            entity.setAddress(dto.getAddress());
            entity.setContactNumber(dto.getContactNumber());

            return entity;
        }

        public static BloodBankDto entityToDto(BloodBankEntity entity) {

            BloodBankDto dto = new BloodBankDto();

            dto.setBloodBankId(entity.getBloodBankId());
            dto.setBloodBankName(entity.getBloodBankName());
            dto.setLicenseNumber(entity.getLicenseNumber());
            dto.setAddress(entity.getAddress());
            dto.setContactNumber(entity.getContactNumber());

            return dto;
        }
    }



