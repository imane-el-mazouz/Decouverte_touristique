package com.tourist.service;

import com.tourist.exception.AccountNotFoundException;
import com.tourist.model.Beneficiary;
import com.tourist.repository.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BeneficiaryService {

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;
    public List<Beneficiary> getAllBeneficiaries(){
        return beneficiaryRepository.findAll();
    }

    public Beneficiary getBeneficiaryById(Long id){
        return beneficiaryRepository.findById(id).orElse(null);
}


    public Beneficiary saveBeneficiary(Beneficiary beneficiary){
        return beneficiaryRepository.save(beneficiary);
}

    public void deleteBeneficiary(Long id) {
        beneficiaryRepository.deleteById(id);
    }

    public Beneficiary updateBeneficiary(Long id, Beneficiary updatedBenef) {
        Beneficiary beneficiary = beneficiaryRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("To Account not found"));

        beneficiary.setName(updatedBenef.getName());
        beneficiary.setRib(updatedBenef.getRib());
        beneficiary.setBank(updatedBenef.getBank());
        beneficiary.setAccount(updatedBenef.getAccount());
        beneficiary.setTransactions(updatedBenef.getTransactions());
        beneficiaryRepository.save(updatedBenef);
        return beneficiary;
    }



}