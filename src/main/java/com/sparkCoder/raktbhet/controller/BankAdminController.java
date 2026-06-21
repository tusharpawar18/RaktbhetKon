package com.sparkCoder.raktbhet.controller;

import com.sparkCoder.raktbhet.dto.BankAdminDto;
import com.sparkCoder.raktbhet.service.BankAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/admin")
public class BankAdminController {

        @Autowired
        private BankAdminService service;


        @PostMapping("/adminreg")
        public ResponseEntity<BankAdminDto> register(@RequestBody BankAdminDto dto)
        {
            BankAdminDto msg=service.register(dto);

            return new ResponseEntity<>(msg, HttpStatus.CREATED);
        }

        @GetMapping("/{id}")
        public ResponseEntity<BankAdminDto> getById(@PathVariable Integer id)
        {
            return  new ResponseEntity<>(service.getById(id),HttpStatus.OK);
        }

        @PutMapping("update/{id}")
        public ResponseEntity<String> update(@PathVariable Integer id, @RequestBody BankAdminDto dto)
        {
            String msg=service.update(id, dto);
            return new ResponseEntity<>(msg, HttpStatus.OK);
        }

        @DeleteMapping("delete/{id}")
        public ResponseEntity<String> delete(@PathVariable Integer id)
        {
            return new ResponseEntity<String>(service.delete(id),HttpStatus.OK);
        }
    }

