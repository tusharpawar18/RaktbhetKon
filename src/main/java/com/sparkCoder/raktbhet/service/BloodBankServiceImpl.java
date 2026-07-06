package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.BloodBankDto;
import com.sparkCoder.raktbhet.entity.BloodBankEntity;
import com.sparkCoder.raktbhet.mapper.BloodBankMapper;
import com.sparkCoder.raktbhet.repository.BloodBankRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
@Service
public class BloodBankServiceImpl implements BloodBankService {

    private static final Logger logger= LoggerFactory.getLogger(BloodBankServiceImpl.class);
        @Autowired
        private BloodBankRepository repository;

        @Override
        public BloodBankDto save(BloodBankDto dto) {
            logger.info("Blood Bank saved successfully");
            BloodBankEntity entity=BloodBankMapper.dtoToEntity(dto);
            BloodBankEntity saved=repository.save(entity);
            return BloodBankMapper.entityToDto(saved);
        }

        @Override
        public BloodBankDto getById(Integer id) {
            logger.info("Blood Bank get successfully");
            BloodBankEntity entity =repository.findById(id).orElseThrow(()->new RuntimeException("Blood Bank Not Found"));
            return BloodBankMapper.entityToDto(entity);
        }

        @Override
        public BloodBankDto update(Integer id, BloodBankDto dto) {
            logger.info("Blood Bank updated successfully");
            BloodBankEntity entity = repository.findById(id).orElseThrow(()->new RuntimeException("Blood Bank Not Found"));

            entity.setBloodBankName(dto.getBloodBankName());
            entity.setLicenseNumber(dto.getLicenseNumber());
            entity.setAddress(dto.getAddress());
            entity.setContactNumber(dto.getContactNumber());

            BloodBankEntity updated = repository.save(entity);
            return BloodBankMapper.entityToDto(updated);
        }

        @Override
        public ResponseEntity<String> delete(Integer id)
        {
            logger.info("Blood Bank deleted successfully");
            repository.deleteById(id);
            return new ResponseEntity<>("Blood Bank Deleted Successfully", HttpStatus.OK);
        }
    }


