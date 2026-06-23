package com.sparkCoder.raktbhet.controller;

import com.sparkCoder.raktbhet.dto.BloodBankDto;
import com.sparkCoder.raktbhet.service.BloodBankService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blood-bank")
public class BloodBankController {

    private static Logger logger= LoggerFactory.getLogger(DonorController.class);
        @Autowired
        private BloodBankService service;


        @PostMapping("/save")
        public BloodBankDto save(@Validated @RequestBody BloodBankDto dto)
        {
            logger.info("bloodbank data is created");
            return service.save(dto);
        }

        @GetMapping("/{id}")
        public BloodBankDto getById(@PathVariable Integer id)
        {
            logger.info("bloodbank data is getting");
            return service.getById(id);
        }

        @PutMapping("/{id}")
        public BloodBankDto update(@PathVariable Integer id,@RequestBody BloodBankDto dto)
        {

            logger.info("bloodbank put is created");
            return service.update(id, dto);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable Integer id)
        {
            logger.info("bloodbank data is delected");
            return service.delete(id);
        }
}
