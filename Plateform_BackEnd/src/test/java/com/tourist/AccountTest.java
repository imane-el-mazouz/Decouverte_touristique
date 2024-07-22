package com.tourist;

import com.tourist.enums.*;
import com.tourist.enums.Bank;
import com.tourist.model.Account;
import com.tourist.model.Card;
import com.tourist.model.User;
import com.tourist.repository.AccountRepository;
import com.tourist.repository.CardRepository;
import com.tourist.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AccountTest {

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private CardRepository cardRepository;

	@InjectMocks
	private AccountService accountService;

	private Account testAccount;
	private Card testCard;

	@BeforeEach
	void setUp() {
		testAccount = new Account();
		testAccount.setIdA(1L);
		testAccount.setTypeA(TypeA.currentAccount);
		testAccount.setSold(1000.0);
		testAccount.setDate(LocalDateTime.of(2023, 1, 1, 10, 0));
		testAccount.setCloseureReason("Closed");
		testAccount.setBank(Bank.cih);
		testAccount.setUser(new User());
		testAccount.setBeneficiaries(new ArrayList<>());
		testAccount.setCards(new ArrayList<>());

		testCard = new Card();
		testCard.setIdC(1L);
		testCard.setExpirationDate(LocalDateTime.now().plusYears(2));
		testCard.setTypeCard(TypeC.debit);
		testCard.setStatus(Status.activated);
		testCard.setBlockingReason(Reason.none);
		testCard.setAccount(testAccount);
	}

	@Test
	public void testGetters() {
		assertEquals(1L, testAccount.getIdA());
		assertEquals(TypeA.currentAccount, testAccount.getTypeA());
		assertEquals(1000.0, testAccount.getSold());
		assertEquals(LocalDateTime.of(2023, 1, 1, 10, 0), testAccount.getDate());
		assertEquals("Closed", testAccount.getCloseureReason());
		assertEquals(Bank.cih, testAccount.getBank());
		assertNotNull(testAccount.getUser());
		assertNotNull(testAccount.getBeneficiaries());
		assertNotNull(testAccount.getCards());
	}

	@Test
	public void testNoArgsConstructor() {
		Account emptyAccount = new Account();
		assertNull(emptyAccount.getIdA());
		assertNull(emptyAccount.getTypeA());
		assertNull(emptyAccount.getSold());
		assertNull(emptyAccount.getDate());
		assertNull(emptyAccount.getCloseureReason());
		assertNull(emptyAccount.getBank());
		assertNull(emptyAccount.getUser());
		assertNull(emptyAccount.getBeneficiaries());
		assertNull(emptyAccount.getCards());
	}

	@Test
	void testGetAllAccounts() {
		when(accountRepository.findAll()).thenReturn(List.of(testAccount));

		List<Account> result = accountService.getAllAccounts();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(testAccount.getIdA(), result.get(0).getIdA());

		verify(accountRepository, times(1)).findAll();
	}

//	@Test
//	void testSaveAccount() {
//		when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
//			Account accountToSave = invocation.getArgument(0);
//			if (accountToSave.getIdA() == null) {
//				accountToSave.setIdA(1L);
//			}
//			return accountToSave;
//		});
//
//		when(cardRepository.save(any(Card.class))).thenReturn(testCard);
//
//		Account savedAccount = accountService.saveAccount(testAccount);
//
//		assertNotNull(savedAccount);
//		assertNotNull(savedAccount.getIdA());
//		assertEquals(testAccount.getTypeA(), savedAccount.getTypeA());
//		assertEquals(testAccount.getSold(), savedAccount.getSold());
//		assertEquals(testAccount.getDate(), savedAccount.getDate());
//		assertEquals(testAccount.getCloseureReason(), savedAccount.getCloseureReason());
//		assertEquals(testAccount.getBank(), savedAccount.getBank());
//		assertEquals(testAccount.getUser(), savedAccount.getUser());
//		assertEquals(testAccount.getBeneficiaries(), savedAccount.getBeneficiaries());
//
//		assertEquals(1, savedAccount.getCards().size());
//		Card savedCard = savedAccount.getCards().get(0);
//
//		assertEquals(testCard.getExpirationDate().truncatedTo(ChronoUnit.SECONDS),
//				savedCard.getExpirationDate().truncatedTo(ChronoUnit.SECONDS));
//
//		assertEquals(testCard.getTypeCard(), savedCard.getTypeCard());
//		assertEquals(testCard.getStatus(), savedCard.getStatus());
//		assertEquals(testCard.getBlockingReason(), savedCard.getBlockingReason());
//
//		verify(accountRepository, times(1)).save(any(Account.class));
//		verify(cardRepository, times(1)).save(any(Card.class));
//	}


	@Test
	void testUpdateAccount() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
		when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

		Account updatedAccount = new Account();
		updatedAccount.setTypeA(TypeA.savingAccount);
		updatedAccount.setSold(2000.0);

		accountService.updateAccount(1L, updatedAccount);

		assertEquals(updatedAccount.getTypeA(), testAccount.getTypeA());
		assertEquals(updatedAccount.getSold(), testAccount.getSold());

		verify(accountRepository, times(1)).findById(1L);
		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	void testCloseAccount() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
		when(accountRepository.save(any(Account.class))).thenReturn(testAccount);

		accountService.closeAccount(1L, "Closed due to request");

		assertEquals("Closed due to request", testAccount.getCloseureReason());

		verify(accountRepository, times(1)).findById(1L);
		verify(accountRepository, times(1)).save(any(Account.class));
	}

	@Test
	void testGetAccountBalance() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

		Double balance = accountService.getAccountBalance(1L);

		assertNotNull(balance);
		assertEquals(testAccount.getSold(), balance);

		verify(accountRepository, times(1)).findById(1L);
	}

//	@Test
//	void testGetAccountTransactions() {
//		when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
//		List<Transaction> transactions = new ArrayList<>(); // Replace with actual mocked transactions if needed
//		List<Transaction> transactions = accountService.getAccountTransactions(1L);
//		assertNotNull(transactions);
//		assertEquals(transactions),
//				verify(accountRepository, times(1)).findById(1L);
//	}

}
