package com.tourist.exception;

public class BeneficiaryNotFoundException extends RuntimeException{
    public BeneficiaryNotFoundException(String beneficiaryNotFound){
        super("beneficiary not found !");
    }
}
