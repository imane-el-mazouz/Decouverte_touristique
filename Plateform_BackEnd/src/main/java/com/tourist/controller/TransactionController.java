package com.tourist.controller;

import com.tourist.model.Transaction;
import com.tourist.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transaction")
@CrossOrigin(origins = "http://localhost:4200")

public class TransactionController {

@Autowired
    private TransactionService transactionService;
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        List<Transaction> transactions =transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
//    @PostMapping("/transfer")
//    public String transferMoney(@RequestBody Transaction request) {
//        return transactionService.transferMoney(request);
//    }

    @PostMapping("/transfer")
    public String transferMoney(@RequestBody Transaction request) {
        return transactionService.transferMoney(
                request.getFromAccount().getIdA(),
                request.getToAccount().getIdA(),
                request.getAmount(),
                request.getDescription()

        );
    }

//    @PostMapping("/extern")
//    public String transferExternally(@RequestParam Long rib, @RequestParam Long ribA,
//                                     @RequestParam Double amount, @RequestParam String description) {
//        return transactionService.transferExternally(rib, ribA, amount, description);
//    }

    @PostMapping("/extern")
    public ResponseEntity<String> transferExternally(@RequestBody Map<String, Object> request) {
        Long ribB = Long.parseLong(request.get("ribB").toString());
        Long ribA = Long.parseLong(request.get("ribA").toString());
        Double amount = Double.parseDouble(request.get("amount").toString());
        String description = request.get("description").toString();

        String result = transactionService.transferExternally(ribB, ribA, amount, description);
        return ResponseEntity.ok(result);
    }
//    @PostMapping("/extern")
//    public ResponseEntity<String> transferExternally(@RequestBody Map<String, Object> request) {
//        Long ribB = Long.parseLong(request.get("ribB").toString());
//        Long ribA = Long.parseLong(request.get("ribA").toString());
//        Double amount = Double.parseDouble(request.get("amount").toString());
//        String description = request.get("description").toString();
//
//        transactionService.transferExternally(ribB, ribA, amount, description);
//
//        return ResponseEntity.ok("External transfer initiated successfully");
//    }


}
