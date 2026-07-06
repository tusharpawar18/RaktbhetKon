package com.sparkCoder.raktbhet.service;

import com.sparkCoder.raktbhet.dto.DonorReqDto;
import com.sparkCoder.raktbhet.dto.DonorResDto;
import com.sparkCoder.raktbhet.entity.AllDataEntity;
import com.sparkCoder.raktbhet.entity.DonorEntity;
import com.sparkCoder.raktbhet.mapper.DonorMapper;
import com.sparkCoder.raktbhet.repository.AllDataRepository;
import com.sparkCoder.raktbhet.repository.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class DonorServiceImpl implements DonorService {

    private static final Logger logger= LoggerFactory.getLogger(DonorServiceImpl.class);
        private final DonorRepository donorRepository;
        private final DonorMapper donorMapper;

        @Autowired
        public DonorServiceImpl(DonorRepository donorRepository, DonorMapper donorMapper) {
            this.donorRepository = donorRepository;
            this.donorMapper = donorMapper;
        }

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private AllDataRepository allDataRepository;

       /* public DonorServiceImpl(DonorRepository donorRepository, DonorMapper donorMapper) {
            this.donorRepository = donorRepository;
            this.donorMapper = donorMapper;
        }*/

        @Override
        public DonorResDto createDonor(DonorReqDto createDTO) {
            logger.info("Donor data successfully");
            if (createDTO == null) throw new IllegalArgumentException("createDTO must not be null");
            if (createDTO.getEmail() != null && donorRepository.existsByEmail(createDTO.getEmail())) {
                throw new IllegalArgumentException("Email already registered");
            }

            DonorEntity entity = donorMapper.toEntityFromCreate(createDTO);
            // Password is stored as provided (no encryption) per request

            // Ensure raktbhetId is set: if not provided, generate next available number
            if (entity.getRaktbhetId() == null) {
                Optional<DonorEntity> top = donorRepository.findTopByOrderByRaktbhetIdDesc();
                int next = 1;
                if (top.isPresent() && top.get().getRaktbhetId() != null) {
                    next = top.get().getRaktbhetId() + 1;
                }
                entity.setRaktbhetId(next);
            }

            DonorEntity saved = donorRepository.save(entity);

            AllDataEntity user = AllDataEntity.builder().userName(entity.getName()).password(bCryptPasswordEncoder.encode(entity.getPassword())).role("DONOR").build();
            allDataRepository.save(user);
            return donorMapper.toDto(saved);
        }

        @Override
        public Optional<DonorResDto> findById(String donorId) {
            logger.info("Donor data findbyId successfully");
            return donorRepository.findById(donorId).map(donorMapper::toDto);
        }

        @Override
        public Optional<DonorResDto> findByEmail(String email) {
            logger.info("Donor data findbyEmail successfully");
            return donorRepository.findByEmail(email).map(donorMapper::toDto);
        }

        @Override
        public Optional<DonorResDto> findByRaktbhetId(Integer raktbhetId) {
            logger.info("Donor data findbyRaktbhetId successfully");
            return donorRepository.findByRaktbhetId(raktbhetId).map(donorMapper::toDto);
        }

        @Override
        public List<DonorResDto> findByBloodGrp(String bloodGrp) {
            logger.info("Donor data findbyBloodGrp successfully");
            return donorRepository.findByBloodGrp(bloodGrp).stream().map(donorMapper::toDto).collect(Collectors.toList());
        }

        @Override
        public void deleteById(String donorId)
        {
            logger.info("Donor data deletedbyId successfully");
            donorRepository.deleteById(donorId);
        }
    }






