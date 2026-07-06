package com.sparkCoder.raktbhet.mapper;

import com.sparkCoder.raktbhet.dto.DonorReqDto;
import com.sparkCoder.raktbhet.dto.DonorResDto;
import com.sparkCoder.raktbhet.entity.DonorEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Mapper between DonorEntity and DTOs.
 */
@Component
public class DonorMapper {

        private final AddressMapper addressMapper;

        @Autowired
        public DonorMapper(AddressMapper addressMapper) {
            this.addressMapper = addressMapper;
        }

        public DonorResDto toDto(DonorEntity entity) {
            if (entity == null) return null;
            DonorResDto dto = new DonorResDto();
            dto.setDonorId(entity.getDonorId());
            dto.setRaktbhetId(entity.getRaktbhetId());
            dto.setName(entity.getName());
            dto.setAge(entity.getAge());
            dto.setGender(entity.getGender());
            dto.setBloodGrp(entity.getBloodGrp());
            dto.setContact(entity.getContact());
            dto.setEmail(entity.getEmail());
            dto.setAddress(addressMapper.toDto(entity.getAddress()));
            return dto;
        }

        public DonorEntity toEntity(DonorResDto dto) {
            if (dto == null) return null;
            DonorEntity entity = new DonorEntity();
            entity.setDonorId(dto.getDonorId());
            entity.setRaktbhetId(dto.getRaktbhetId());
            entity.setName(dto.getName());
            entity.setAge(dto.getAge());
            entity.setGender(dto.getGender());
            entity.setBloodGrp(dto.getBloodGrp());
            entity.setContact(dto.getContact());
            entity.setEmail(dto.getEmail());
            entity.setAddress(addressMapper.toEntity(dto.getAddress()));
            return entity;
        }

        public DonorEntity toEntityFromCreate(DonorReqDto dto) {
            if (dto == null) return null;
            DonorEntity entity = new DonorEntity();

            entity.setName(dto.getName());
            entity.setAge(dto.getAge());
            entity.setGender(dto.getGender());
            entity.setBloodGrp(dto.getBloodGrp());
            entity.setContact(dto.getContact());
            entity.setEmail(dto.getEmail());
            entity.setPassword(dto.getPassword());
            entity.setAddress(addressMapper.toEntity(dto.getAddress()));
            return entity;
        }
    }



