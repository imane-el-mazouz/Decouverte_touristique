package com.tourist.service;

import com.tourist.enums.Reason;
import com.tourist.enums.Status;
import com.tourist.enums.TypeC;
import com.tourist.exception.AccountNotFoundException;
import com.tourist.exception.UserNotFoundException;
import com.tourist.model.Account;
import com.tourist.model.Card;
import com.tourist.model.Transaction;
import com.tourist.model.User;
import com.tourist.repository.AccountRepository;
import com.tourist.repository.CardRepository;
import com.tourist.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private UserService userService;

  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  public Account getAccountById(Long id) {
    return accountRepository.findById(id)
      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
  }

  public List<Account> getAccountsByUserId(Long idU) {
    return accountRepository.findByUserId(idU);
  }

  @Transactional
  public Account saveAccount(String username, Account account) {
    User user = userRepository.findByName(username)
      .orElseThrow(() -> new UserNotFoundException("User not found!"));
    account.setUser(user);
    Account savedAccount = accountRepository.save(account);

    Card card = new Card();
    card.setExpirationDate(LocalDateTime.now().plusYears(2));
    card.setTypeCard(TypeC.debit);
    card.setStatus(Status.activated);
    card.setBlockingReason(Reason.none);
    card.setAccount(savedAccount);
    Card savedCard = cardRepository.save(card);
    savedAccount.getCards().add(savedCard);

    return accountRepository.save(savedAccount);
  }

//  public Account saveAccount(Account account, Long userId) {
//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//   userId = (Long) authentication.getPrincipal(); // Adjust based on your implementation
//
//    User user = userRepository.findById(userId)
//      .orElseThrow(() -> new UserNotFoundException("User not found!"));
//
//    account.setUser(user);
//
//    // Save the account
//    Account savedAccount = accountRepository.save(account);
//
//    // Create and set up a new card
//    Card card = new Card();
//    card.setExpirationDate(LocalDateTime.now().plusYears(2));
//    card.setTypeCard(TypeC.debit);
//    card.setStatus(Status.activated);
//    card.setBlockingReason(Reason.none);
//    card.setAccount(savedAccount);
//
//    // Save the card
//    Card savedCard = cardRepository.save(card);
//
//    // Add the card to the account's card list
//    savedAccount.getCards().add(savedCard);
//
//    // Save the updated account with the new card
//    return accountRepository.save(savedAccount);
//  }

  @Transactional
  public void deleteAccount(Long id) {
    Account account = accountRepository.findById(id)
      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    cardRepository.deleteCardByAccountIdA(id);
    accountRepository.deleteById(id);
  }

  @Transactional
//  public void updateAccount(Long id, Account updatedAccount) {
//    Account existingAccount = accountRepository.findById(id)
//      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//
//    existingAccount.setTypeA(updatedAccount.getTypeA());
//    existingAccount.setSold(updatedAccount.getSold());
//    existingAccount.setDate(updatedAccount.getDate());
//    existingAccount.setAccountClosed(updatedAccount.getAccountClosed());
//    existingAccount.setCloseureReason(updatedAccount.getCloseureReason());
//    existingAccount.setBank(updatedAccount.getBank());
//    existingAccount.setUser(updatedAccount.getUser());
//    existingAccount.setBeneficiaries(updatedAccount.getBeneficiaries());
//    existingAccount.setCards(updatedAccount.getCards());
//
//    accountRepository.save(existingAccount);
//  }
//  public Account updateAccount(Long id, Account updatedAccount) {
//    Account existingAccount = accountRepository.findById(id)
//      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//    existingAccount.setTypeA(updatedAccount.getTypeA());
//    existingAccount.setSold(updatedAccount.getSold());
//    existingAccount.setDate(updatedAccount.getDate());
//    existingAccount.setAccountClosed(updatedAccount.getAccountClosed());
//    existingAccount.setCloseureReason(updatedAccount.getCloseureReason());
//    existingAccount.setBank(updatedAccount.getBank());
//    existingAccount.setUser(updatedAccount.getUser());
//    existingAccount.setBeneficiaries(updatedAccount.getBeneficiaries());
//    existingAccount.setCards(updatedAccount.getCards());
//
//    return accountRepository.save(existingAccount);
//  }
  public Account updateAccount(Long id, Account account) throws AccountNotFoundException {
    return accountRepository.findById(id).map(existingAccount -> {
      existingAccount.setTypeA(account.getTypeA());
      existingAccount.setSold(account.getSold());
      existingAccount.setDate(account.getDate());
      existingAccount.setBank(account.getBank());
      return accountRepository.save(existingAccount);
    }).orElseThrow(() -> new AccountNotFoundException("Account not found with id " + id));
  }

//  public Account updateAccount(Long id, Account updatedAccount) {
//    Account existingAccount = accountRepository.findById(id)
//      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//    existingAccount.setTypeA(updatedAccount.getTypeA());
//    existingAccount.setSold(updatedAccount.getSold());
//    existingAccount.setDate(updatedAccount.getDate());
//    existingAccount.setAccountClosed(updatedAccount.getAccountClosed());
//    existingAccount.setCloseureReason(updatedAccount.getCloseureReason());
//    existingAccount.setBank(updatedAccount.getBank());
//    existingAccount.setUser(updatedAccount.getUser());
//    existingAccount.setBeneficiaries(updatedAccount.getBeneficiaries());
//    existingAccount.setCards(updatedAccount.getCards());
//
//    return accountRepository.save(existingAccount);
//  }



  @Transactional
  public void closeAccount(Long id, String reason) {
    Account account = accountRepository.findById(id)
      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    if (account.getSold() != 0) {
      throw new IllegalStateException("Account balance must be zero to close the account");
    }
    account.setAccountClosed(true);
    account.setCloseureReason(reason);
    accountRepository.save(account);
  }

  public Double getAccountBalance(Long id) {
    Account account = accountRepository.findById(id)
      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    return account.getSold();
  }

  public List<Transaction> getAccountTransactions(Long id) {
    Account account = accountRepository.findById(id)
      .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    return account.getTransactions();
  }

  public List<Account> getAccountsByUsername(String username) {
    User user = userRepository.findByName(username).get();
    return accountRepository.findByUserId(user.getIdU());
  }
}


//package com.bank.service;
//
//import com.bank.enums.Reason;
//import com.bank.enums.Status;
//import com.bank.enums.TypeC;
//import com.bank.exception.AccountNotFoundException;
//import com.bank.exception.UserNotFoundException;
//import com.bank.model.Account;
//import com.bank.model.Card;
//import com.bank.model.Transaction;
//import com.bank.model.User;
//import com.bank.repository.AccountRepository;
//import com.bank.repository.CardRepository;
//import com.bank.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class AccountService {
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Autowired
//    private CardRepository cardRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Autowired
//    private UserService userService;
//
//    public List<Account> getAllAccounts() {
//        return accountRepository.findAll();
//    }
//
//    public Account getAccountById(Long id) {
//        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException("Account not found"));
//    }
//
////    public List<Account> getAccountsByUserId(Long idU) {
////        return accountRepository.findByIdU(idU);
////    }
//
//    public List<Account> getAccountsByUserId(Long idU) {
//        return accountRepository.findByUserId(idU);
//    }
////    @Transactional
////    public Account saveAccount(Account account) {
////        Account savedAccount = accountRepository.save(account);
////
////        Card card = new Card();
////        card.setExpirationDate(LocalDateTime.now().plusYears(2));
////        card.setTypeCard(TypeC.debit);
////        card.setStatus(Status.activated);
////        card.setBlockingReason(Reason.none);
////        card.setAccount(savedAccount);
////
////        cardRepository.save(card);
////        List<Card> cards = new ArrayList<>();
////        cards.add(card);
////        savedAccount.setCards(cards);
////
////        return accountRepository.save(savedAccount);
////    }
//
////
////    @Transactional
////    public Account saveAccount(Account account, Long userId) {
////        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
////        account.setUser(user);
////
////        Card card = new Card();
////        card.setExpirationDate(LocalDateTime.now().plusYears(2));
////        card.setTypeCard(TypeC.debit);
////        card.setStatus(Status.activated);
////        card.setBlockingReason(Reason.none);
////        card.setAccount(account);
////        cardRepository.save(card);
////
////        account.getCards().add(card);
////        List<Card> cards = new ArrayList<>();
////        cards.add(card);
////        account.setCards(cards);
////
////        return accountRepository.save(account);
////    }
//
//
//    public Account saveAccount(Long userId, Account account) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isEmpty()) {
//            throw new UserNotFoundException("user not found !");
//        }
//        User user = userOptional.get();
//
//        account.setUser(user);
//        account.setRib(account.getRib());
//        Account savedAccount = accountRepository.save(account);
//
//        Card card = new Card();
//        card.setAccount(savedAccount);
//        Card savedCard = cardRepository.save(card);
//        card.setExpirationDate(LocalDateTime.now().plusYears(2));
//        card.setTypeCard(TypeC.debit);
//        card.setStatus(Status.activated);
//        card.setBlockingReason(Reason.none);
//        card.setAccount(account);
//        savedAccount.getCards().add(savedCard);
//
//        return accountRepository.save(savedAccount);
//    }
//
//    @Transactional
//    public void deleteAccount(Long id) {
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//        cardRepository.deleteCardByAccountIdA(id);
//        accountRepository.deleteById(id);
//    }
//
//    @Transactional
//    public Account updateAccount(Long id, Account updatedAccount) {
//        Account existingAccount = accountRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//
//        existingAccount.setTypeA(updatedAccount.getTypeA());
//        existingAccount.setSold(updatedAccount.getSold());
//        existingAccount.setDate(updatedAccount.getDate());
//        existingAccount.setAccountClosed(updatedAccount.getAccountClosed());
//        existingAccount.setCloseureReason(updatedAccount.getCloseureReason());
//        existingAccount.setBank(updatedAccount.getBank());
//        existingAccount.setUser(updatedAccount.getUser());
//        existingAccount.setBeneficiaries(updatedAccount.getBeneficiaries());
//        existingAccount.setCards(updatedAccount.getCards());
//
//        return accountRepository.save(existingAccount);
//    }
//
//    @Transactional
//    public void closeAccount(Long id, String reason) {
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//
//        if (account.getSold() != 0) {
//            throw new IllegalStateException("Account balance must be zero to close the account");
//        }
//
//        account.setAccountClosed(true);
//        account.setCloseureReason(reason);
//        accountRepository.save(account);
//    }
//
//    public Double getAccountBalance(Long id) {
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//        return account.getSold();
//    }
//
//    public List<Transaction> getAccountTransactions(Long id) {
//        Account account = accountRepository.findById(id)
//                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//        return account.getTransactions();
//    }
//}
//
////@Transactional
////public Account saveAccount(Account account, Long userId) {
////    User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + userId));
////    account.setUser(user);
////
////    Card card = new Card();
////    card.setExpirationDate(LocalDateTime.now().plusYears(2));
////    card.setTypeCard(TypeC.debit);
////    card.setStatus(Status.activated);
////    card.setBlockingReason(Reason.none);
////    card.setAccount(account);
////    cardRepository.save(card);
////
////    account.getCards().add(card);
////    List<Card> cards = new ArrayList<>();
////    cards.add(card);
////    account.setCards(cards);
////
////    return accountRepository.save(account);
////}
////
////    @Transactional
////    public void deleteAccount(Long id) {
////        Account account = accountRepository.findById(id)
////                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
////        cardRepository.deleteCardByAccountIdA(id);
////        accountRepository.deleteById(id);
////    }
////
////    @Transactional
////    public Account updateAccount(Long id, Account updatedAccount) {
////        Account existingAccount = accountRepository.findById(id)
////                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
////
////        existingAccount.setTypeA(updatedAccount.getTypeA());
////        existingAccount.setSold(updatedAccount.getSold());
////        existingAccount.setDate(updatedAccount.getDate());
////        existingAccount.setAccountClosed(updatedAccount.getAccountClosed());
////        existingAccount.setCloseureReason(updatedAccount.getCloseureReason());
////        existingAccount.setBank(updatedAccount.getBank());
////        existingAccount.setUser(updatedAccount.getUser());
////        existingAccount.setBeneficiaries(updatedAccount.getBeneficiaries());
////        existingAccount.setCards(updatedAccount.getCards());
////
////        return accountRepository.save(existingAccount);
////    }
////
////    @Transactional
////    public void closeAccount(Long id, String reason) {
////        Account account = accountRepository.findById(id)
////                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
////
////        if (account.getSold() != 0) {
////            throw new IllegalStateException("Account balance must be zero to close the account");
////        }
////
////        account.setAccountClosed(true);
////        account.setCloseureReason(reason);
////        accountRepository.save(account);
////    }
////
//public Double getAccountBalance(Long id) {
//    Account account = accountRepository.findById(id)
//            .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//    return account.getSold();
//}
////
//public List<Transaction> getAccountTransactions(Long id) {
//    Account account = accountRepository.findById(id)
//            .orElseThrow(() -> new AccountNotFoundException("Account not found"));
//    return account.getTransactions();
//}
////}
//
//}

