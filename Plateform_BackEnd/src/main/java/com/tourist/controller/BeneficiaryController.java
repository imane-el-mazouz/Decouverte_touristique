package com.tourist.controller;

import com.tourist.model.Beneficiary;
import com.tourist.service.BeneficiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/benef")
@CrossOrigin(origins = "http://localhost:4200")

public class BeneficiaryController {

    @Autowired
    private BeneficiaryService beneficiaryService;

        @GetMapping
        public ResponseEntity<List<Beneficiary>> getAllBeneficiaries() {
            List<Beneficiary> beneficiaries = beneficiaryService.getAllBeneficiaries();
            return ResponseEntity.ok(beneficiaries);
        }

        @GetMapping("/{id}")
        public ResponseEntity<Beneficiary> getBeneficiaryById(@PathVariable String id) {
            Beneficiary beneficiary = beneficiaryService.getBeneficiaryById(Long.valueOf(id));
            if (beneficiary != null) {
                return ResponseEntity.ok(beneficiary);
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PostMapping
        public Beneficiary saveBeneficiary(@RequestBody Beneficiary beneficiary) {
            return beneficiaryService.saveBeneficiary(beneficiary);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteBeneficiary(@PathVariable Long id) {
            if (beneficiaryService.getBeneficiaryById(id) != null) {
                beneficiaryService.deleteBeneficiary(id);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }

        @PutMapping("/edit/{id}")
        public ResponseEntity<Void> updateBeneficiary(@PathVariable Long id, @RequestBody Beneficiary beneficiary) {
            if (beneficiaryService.getBeneficiaryById(id) != null) {
                beneficiaryService.updateBeneficiary(id, beneficiary);
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        }
}
