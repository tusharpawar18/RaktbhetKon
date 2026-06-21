package com.sparkCoder.raktbhet.controller;

import com.sparkCoder.raktbhet.dto.DonorReqDto;
import com.sparkCoder.raktbhet.dto.DonorResDto;
import com.sparkCoder.raktbhet.service.DonorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/donors")
public class DonorController {

        private final DonorService donorService;

        public DonorController(DonorService donorService) {
            this.donorService = donorService;
        }

        @PostMapping
        public ResponseEntity<?> createDonor(@RequestBody DonorReqDto req) {
            try {
                DonorResDto created = donorService.createDonor(req);
                URI location = URI.create(String.format("/donors/%s", created.getDonorId()));
                return ResponseEntity.created(location).body(created);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<DonorResDto> getById(@PathVariable("id") String id) {
            return donorService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/by-email")
        public ResponseEntity<DonorResDto> getByEmail(@RequestParam("email") String email) {
            return donorService.findByEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/raktbhet/{raktbhetId}")
        public ResponseEntity<DonorResDto> getByRaktbhet(@PathVariable("raktbhetId") Integer raktbhetId) {
            return donorService.findByRaktbhetId(raktbhetId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/blood/{bloodGrp}")
        public ResponseEntity<List<DonorResDto>> getByBloodGrp(@PathVariable("bloodGrp") String bloodGrp) {
            List<DonorResDto> list = donorService.findByBloodGrp(bloodGrp);
            return ResponseEntity.ok(list);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteById(@PathVariable("id") String id) {
            donorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

