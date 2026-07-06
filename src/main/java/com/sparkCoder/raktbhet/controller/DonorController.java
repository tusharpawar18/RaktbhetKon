package com.sparkCoder.raktbhet.controller;

import com.sparkCoder.raktbhet.dto.DonorReqDto;
import com.sparkCoder.raktbhet.dto.DonorResDto;
import com.sparkCoder.raktbhet.service.DonorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@RestController
@RequestMapping("/donors")
public class DonorController { 

    private static Logger logger=LoggerFactory.getLogger(DonorController.class);
        private final DonorService donorService;

        public DonorController(DonorService donorService) {
            this.donorService = donorService;
        }

        @PostMapping("save")
        public ResponseEntity<?> createDonor(@Validated @RequestBody DonorReqDto req) {

            logger.info("Donar is created");
            try {
                DonorResDto created = donorService.createDonor(req);
                URI location = URI.create(String.format("/donors/%s", created.getDonorId()));
                return ResponseEntity.created(location).body(created);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(ex.getMessage());
            }
        }

        @GetMapping("/{id}")
        public ResponseEntity<DonorResDto> getById(@Validated @PathVariable("id") String id) {

            logger.info("Donor Get  By Id  created");
            return donorService.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/by-email")
        public ResponseEntity<DonorResDto> getByEmail(@Validated @RequestParam("email") String email) {
            logger.info("Donor Get  By Email created");
            return donorService.findByEmail(email).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/raktbhet/{raktbhetId}")
        public ResponseEntity<DonorResDto> getByRaktbhet(@Validated @PathVariable("raktbhetId") Integer raktbhetId) {
            logger.info(" Donor Get  By Raktbhet Id created");
            return donorService.findByRaktbhetId(raktbhetId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        }

        @GetMapping("/blood/{bloodGrp}")
        public ResponseEntity<List<DonorResDto>> getByBloodGrp(@Validated@PathVariable("bloodGrp") String bloodGrp) {

            logger.info(" Donor Get  By Blood Group created");
            List<DonorResDto> list = donorService.findByBloodGrp(bloodGrp);
            return ResponseEntity.ok(list);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteById(@Validated @PathVariable("id") String id) {
            logger.info("Delete Donor");
            donorService.deleteById(id);
            return ResponseEntity.noContent().build();
        }



    @GetMapping("/location")
    public ResponseEntity<List<DonorResDto>> getDonorsByLocation(
            @RequestParam String city,
            @RequestParam String state,
            @RequestParam String pincode) {

        logger.info("Get Donors By Location");

        List<DonorResDto> donors = donorService.findByLocation(city, state, pincode);
        return ResponseEntity.ok(donors);
    }
}

