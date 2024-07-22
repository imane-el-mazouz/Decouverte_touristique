package com.tourist.service;

import com.tourist.enums.TypeC;
import com.tourist.enums.TypeTransaction;
import com.tourist.exception.AccountNotFoundException;
import com.tourist.exception.BeneficiaryNotFoundException;
import com.tourist.model.Account;
import com.tourist.model.Beneficiary;
import com.tourist.model.Transaction;
import com.tourist.repository.AccountRepository;
import com.tourist.repository.BeneficiaryRepository;
import com.tourist.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BeneficiaryRepository beneficiaryRepository;


    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }


    public Double getAccountSold(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("To Account not found"));
        return account.getSold();
    }

    public List<Transaction> getAccountTransactions(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("To Account not found"));
        return account.getTransactions();
    }
    public String transferMoney(Long fromAccountId, Long toAccountId, Double amount, String description) {
        Optional<Account> fromAccountOpt = accountRepository.findById(fromAccountId);
        Optional<Account> toAccountOpt = accountRepository.findById(toAccountId);

        Account fromAccount = fromAccountOpt.orElseThrow(() -> new AccountNotFoundException("From Account not found"));
        Account toAccount = toAccountOpt.orElseThrow(() -> new AccountNotFoundException("To Account not found"));

        if (fromAccount.getSold() < amount) {
            throw new IllegalStateException("Insufficient funds in the source account");
        }

        fromAccount.setSold(fromAccount.getSold() - amount);
        toAccount.setSold(toAccount.getSold() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction debitTransaction = new Transaction();
        debitTransaction.setDate(LocalDateTime.now());
        debitTransaction.setAmount(amount);
        debitTransaction.setDescription(description);
        debitTransaction.setFromAccount(fromAccount);
        debitTransaction.setToAccount(toAccount);
        debitTransaction.setTypeCard(TypeC.debit);
        debitTransaction.setTypeT(TypeTransaction.internal);
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setDate(LocalDateTime.now());
        creditTransaction.setAmount(amount);
        creditTransaction.setDescription(description);
        creditTransaction.setFromAccount(fromAccount);
        creditTransaction.setToAccount(toAccount);
        creditTransaction.setTypeCard(TypeC.credit);
        creditTransaction.setTypeT(TypeTransaction.internal);
        transactionRepository.save(creditTransaction);

        return "Money transferred successfully";
    }

    public String transferExternally(Long ribB, Long ribA, Double amount, String description) {
        Account account = accountRepository.findByRib(ribA)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        Beneficiary beneficiary = beneficiaryRepository.findByRib(ribB)
                .orElseThrow(() -> new BeneficiaryNotFoundException("Beneficiary not found"));

        if (account.getSold() < amount) {
            throw new IllegalStateException("Insufficient funds in the source account");
        }

        account.setSold(account.getSold() - amount);
        beneficiary.setSold(beneficiary.getSold() + amount);

        accountRepository.save(account);
        beneficiaryRepository.save(beneficiary);

        Transaction debitTransaction = new Transaction();
        debitTransaction.setDate(LocalDateTime.now());
        debitTransaction.setAmount(amount);
        debitTransaction.setDescription(description);
        debitTransaction.setFromAccount(account);
        debitTransaction.setBeneficiary(beneficiary);
        debitTransaction.setTypeCard(TypeC.debit);
        debitTransaction.setTypeT(TypeTransaction.external);
        transactionRepository.save(debitTransaction);

        Transaction creditTransaction = new Transaction();
        creditTransaction.setDate(LocalDateTime.now());
        creditTransaction.setAmount(amount);
        creditTransaction.setDescription(description);
        creditTransaction.setBeneficiary(beneficiary);
        creditTransaction.setTypeCard(TypeC.credit);
        creditTransaction.setTypeT(TypeTransaction.external);
        transactionRepository.save(creditTransaction);

        return "External transfer to beneficiary completed successfully";
    }
}


