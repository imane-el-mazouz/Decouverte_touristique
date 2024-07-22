//package com.bank.controller;
//
//import com.bank.model.Account;
//import com.bank.model.Transaction;
//import com.bank.service.AccountService;
//import com.bank.service.TransactionService;
//import com.bank.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@RestController
//@RequestMapping("/api/account")
//@CrossOrigin(origins = "http://localhost:4200")
//
//
//public class AccountController {
//
//   @Autowired
//   private AccountService accountService;
//
//    @Autowired
//    private TransactionService transactionService;
//
//    @Autowired
//    private UserService userService;
//
////  @GetMapping
////    public ResponseEntity<List<Account>> getAllAccounts() {
////        List<Account> accounts = accountService.getAllAccounts();
////        return ResponseEntity.ok(accounts);
////    }
//
//  @GetMapping
//  public ResponseEntity<List<Account>> getAllAccounts() {
//    List<Account> accounts = accountService.getAllAccounts();
//    return ResponseEntity.ok(accounts);
//  }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
//        Account account = accountService.getAccountById(id);
//        return ResponseEntity.ok(account);
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
//        List<Account> accounts = accountService.getAccountsByUserId(userId);
//        return ResponseEntity.ok(accounts);
//    }
//
////    @PostMapping("/user/{userId")
////    public Account saveAccount(@RequestBody Account account) {
////        return accountService.saveAccount(account);
////    }
//
//
//    @PostMapping("/save/{userId}")
//    public ResponseEntity<Account> saveAccountForUser(@PathVariable Long userId, @RequestBody Account account) {
//        try {
//            Account savedAccount = accountService.saveAccount(userId, account);
//            return ResponseEntity.ok(savedAccount);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
////@PostMapping("/save/{userId}")
////public ResponseEntity<Account> saveAccountForUser(@PathVariable Long userId, @RequestBody Account account) {
////    try {
////        Account savedAccount = accountService.saveAccount(userId, account);
////        return ResponseEntity.ok(savedAccount);
////    } catch (IllegalArgumentException e) {
////        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
////    } catch (Exception e) {
////        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
////    }
////}
//
////    @PostMapping("/save/{userId}")
////    public ResponseEntity<Account> saveAccount(@PathVariable Long userId, @Valid @RequestBody Account account) {
////        Account savedAccount = accountService.saveAccount(userId, account);
////        return ResponseEntity.ok(savedAccount);
////    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
//        accountService.deleteAccount(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
//        Account updatedAccount = accountService.updateAccount(id, account);
//        return ResponseEntity.ok(updatedAccount);
//    }
//
//    @PutMapping("/close/{id}")
//    public ResponseEntity<Void> closeAccount(@PathVariable Long id, @RequestBody Map<String, String> request) {
//        String reason = request.get("reason");
//        if (reason == null || reason.isEmpty()) {
//            return ResponseEntity.badRequest().build();
//        }
//        accountService.closeAccount(id, reason);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping("/{id}/balance")
//    public ResponseEntity<Double> getAccountBalance(@PathVariable Long id) {
//        Double balance = accountService.getAccountBalance(id);
//        return ResponseEntity.ok(balance);
//    }
////
//    @GetMapping("/{id}/transactions")
//    public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long id) {
//        List<Transaction> transactions = accountService.getAccountTransactions(id);
//        return ResponseEntity.ok(transactions);
//    }
//}

package com.tourist.controller;

import com.tourist.exception.AccountNotFoundException;
import com.tourist.model.Account;
import com.tourist.model.Transaction;
import com.tourist.service.AccountService;
import com.tourist.service.TransactionService;
import com.tourist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/account")
@CrossOrigin(origins = "*")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private TransactionService transactionService;

  @Autowired
  private UserService userService;

  @GetMapping("/all")
  public ResponseEntity<List<Account>> getAllAccounts() {
    List<Account> accounts = accountService.getAllAccounts();
    return ResponseEntity.ok(accounts);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    Account account = accountService.getAccountById(id);
    return ResponseEntity.ok(account);
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
    List<Account> accounts = accountService.getAccountsByUserId(userId);
    return ResponseEntity.ok(accounts);
  }

//  @PostMapping("/save/{userId}")
//  public ResponseEntity<Account> saveAccountForUser(@PathVariable Long userId, @RequestBody Account account) {
//    try {
//      Account savedAccount = accountService.saveAccount(userId, account);
//      return ResponseEntity.ok(savedAccount);
//    } catch (IllegalArgumentException e) {
//      return ResponseEntity.notFound().build();
//    } catch (Exception e) {
//      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//    }
//  }
//@PostMapping("/save")
//public ResponseEntity<Account> saveAccount(@RequestBody Account account) {
//  Account savedAccount = accountService.saveAccount(account);
//  return new ResponseEntity<>(savedAccount, HttpStatus.CREATED);
//}
@PostMapping("/save")
public ResponseEntity<Account> addAccount(@RequestBody Account account) {
  Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
  String username = loggedInUser.getName();
  account = accountService.saveAccount(username, account);
  return ResponseEntity.ok(account);
}

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
    accountService.deleteAccount(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/close/{id}")
  public ResponseEntity<Void> closeAccount(@PathVariable Long id, @RequestBody Map<String, String> request) {
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    String username = loggedInUser.getName();
    String reason = request.get("reason");
//    if (reason == null || reason.isEmpty()) {
//      return ResponseEntity.badRequest().build();
//    }
    accountService.closeAccount(id, reason);
    return ResponseEntity.noContent().build();
  }
//  @PutMapping("/{id}")
//  public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
//    Account updatedAccount = accountService.updateAccount(id, account);
//    return ResponseEntity.ok(updatedAccount);
//  }
@PutMapping("/update/{id}")
public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account account) {
  try {
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    String username = loggedInUser.getName();
    Account updatedAccount = accountService.updateAccount(id, account);
    return ResponseEntity.ok(updatedAccount);
  } catch (AccountNotFoundException e) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
  } catch (Exception e) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
  }
}


  @GetMapping("/{id}/balance")
  public ResponseEntity<Double> getAccountBalance(@PathVariable Long id) {
    Double balance = accountService.getAccountBalance(id);
    return ResponseEntity.ok(balance);
  }

  @GetMapping("/{id}/transactions")
  public ResponseEntity<List<Transaction>> getAccountTransactions(@PathVariable Long id) {
    List<Transaction> transactions = accountService.getAccountTransactions(id);
    return ResponseEntity.ok(transactions);
  }
  @GetMapping("/accounts")
  public ResponseEntity<List<Account>> getUserAccounts(Authentication authentication) {
    String username = authentication.getName();
    List<Account> accounts = accountService.getAccountsByUsername(username);
    return ResponseEntity.ok(accounts);
  }
}
