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

    private static final Logger logger = LoggerFactory.getLogger(DonorServiceImpl.class);
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

    @Override
    public DonorResDto createDonor(DonorReqDto createDTO) {
        logger.info("[DonorServiceImpl] Creating new donor with name: {}", createDTO.getName());
        
        if (createDTO == null) throw new IllegalArgumentException("createDTO must not be null");
        if (createDTO.getEmail() != null && donorRepository.existsByEmail(createDTO.getEmail())) {
            logger.warn("[DonorServiceImpl] Email already registered: {}", createDTO.getEmail());
            throw new IllegalArgumentException("Email already registered");
        }

        // ✓ NEW: Check if username already exists
        String username = createDTO.getName();
        if (allDataRepository.existsByUserName(username)) {
            logger.error("[DonorServiceImpl] Username already exists: {}", username);
            throw new IllegalArgumentException("Username already exists: " + username + ". Please choose a different username.");
        }

        DonorEntity entity = donorMapper.toEntityFromCreate(createDTO);
        logger.info("[DonorServiceImpl] Donor entity created from DTO");

        // Ensure raktbhetId is set: if not provided, generate next available number
        if (entity.getRaktbhetId() == null) {
            Optional<DonorEntity> top = donorRepository.findTopByOrderByRaktbhetIdDesc();
            int next = 1;
            if (top.isPresent() && top.get().getRaktbhetId() != null) {
                next = top.get().getRaktbhetId() + 1;
            }
            entity.setRaktbhetId(next);
            logger.info("[DonorServiceImpl] Generated RaktbhetId: {}", next);
        }

        DonorEntity saved = donorRepository.save(entity);
        logger.info("[DonorServiceImpl] Donor saved with ID: {}", saved.getDonorId());

        // Create user in AllData table with BCrypt encoded password
        String encodedPassword = bCryptPasswordEncoder.encode(entity.getPassword());
        logger.info("[DonorServiceImpl] Password encoded using BCryptPasswordEncoder");
        logger.debug("[DonorServiceImpl] Encoded password hash (first 20 chars): {}...", 
                     encodedPassword.substring(0, Math.min(20, encodedPassword.length())));
        
        AllDataEntity user = AllDataEntity.builder()
                .userName(username)
                .password(encodedPassword)
                .role("DONOR")
                .build();
        
        try {
            allDataRepository.save(user);
            logger.info("[DonorServiceImpl] User created in AllData table with username: {} and role: DONOR", username);
        } catch (Exception e) {
            logger.error("[DonorServiceImpl] Error creating user in AllData table: {}", e.getMessage());
            throw new RuntimeException("Failed to create user account: " + e.getMessage(), e);
        }
        
        return donorMapper.toDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonorResDto> findById(String donorId) {
        logger.info("[DonorServiceImpl] Finding donor by ID: {}", donorId);
        return donorRepository.findById(donorId).map(donorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonorResDto> findByEmail(String email) {
        logger.info("[DonorServiceImpl] Finding donor by email: {}", email);
        return donorRepository.findByEmail(email).map(donorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DonorResDto> findByRaktbhetId(Integer raktbhetId) {
        logger.info("[DonorServiceImpl] Finding donor by RaktbhetId: {}", raktbhetId);
        return donorRepository.findByRaktbhetId(raktbhetId).map(donorMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorResDto> findByBloodGrp(String bloodGrp) {
        logger.info("[DonorServiceImpl] Finding donors by blood group: {}", bloodGrp);
        return donorRepository.findByBloodGrp(bloodGrp).stream().map(donorMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(String donorId) {
        logger.info("[DonorServiceImpl] Deleting donor by ID: {}", donorId);
        donorRepository.deleteById(donorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DonorResDto> findByLocation(String city, String state, String pincode) {
        logger.info("[DonorServiceImpl] Finding donors by location - City: {}, State: {}, Pincode: {}", city, state, pincode);

        return donorRepository
                .findByAddress_CityAndAddress_StateAndAddress_Pincode(city, state, pincode)
                .stream()
                .map(donorMapper::toDto)
                .collect(Collectors.toList());
    }
}
