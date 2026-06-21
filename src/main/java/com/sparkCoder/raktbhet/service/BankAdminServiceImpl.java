package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.BankAdminDto;
import com.sparkCoder.raktbhet.entity.BankAdminEntity;
import com.sparkCoder.raktbhet.mapper.BankAdminMapper;
import com.sparkCoder.raktbhet.repository.BankAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class BankAdminServiceImpl implements BankAdminService {
        @Autowired(required = true)
        private BankAdminRepository repositoryAdmin;

        @Override
        public BankAdminDto register(BankAdminDto dto) {

            BankAdminEntity admin = BankAdminMapper.dtoToEntity(dto);
            BankAdminEntity saved = repositoryAdmin.save(admin);
            return BankAdminMapper.entityToDto(saved);
        }


        @Override
        public BankAdminDto getById(Integer id) {

            BankAdminEntity admin = repositoryAdmin.findById(id).orElseThrow(() -> new RuntimeException("Admin Not Found"));
            return BankAdminMapper.entityToDto(admin);
        }

        @Override
        public String update(Integer id, BankAdminDto dto) {

            BankAdminEntity admin = repositoryAdmin.findById(id).orElseThrow(() -> new RuntimeException("Admin Not Found"));

            admin.setName(dto.getName());
            admin.setAge(dto.getAge());
            admin.setGender(dto.getGender());
            admin.setContactNumber(dto.getContactNumber());
            admin.setBankId(dto.getBankId());
            admin.setIdentityNumber(dto.getIdentityNumber());
            admin.setUserName(dto.getUserName());
            admin.setPassword(dto.getPassword());

            BankAdminEntity updated = repositoryAdmin.save(admin);
            return "Bank Admin data is upsated Successfully....!!";
        }

        @Override
        public String delete(Integer id) {

            repositoryAdmin.deleteById(id);
            return "Admin Deleted Successfully";
        }
    }



