package com.sparkCoder.raktbhet.controller;

import com.sparkCoder.raktbhet.dto.BloodBankDto;
import com.sparkCoder.raktbhet.service.BloodBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BloodBnakController
{

    @RestController
    @RequestMapping("/blood-bank")
    public class BloodBankController {

        @Autowired
        private BloodBankService service;


        @PostMapping("/save")
        public BloodBankDto save(@RequestBody BloodBankDto dto)
        {
            return service.save(dto);
        }

        @GetMapping("/{id}")
        public BloodBankDto getById(@PathVariable Integer id) {

            return service.getById(id);
        }

        @PutMapping("/{id}")
        public BloodBankDto update(@PathVariable Integer id,@RequestBody BloodBankDto dto) {

            return service.update(id, dto);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<String> delete(@PathVariable Integer id) {

            return service.delete(id);
        }
    }
}
