package com.tourist;

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
import com.tourist.service.AccountService;
import com.tourist.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionTest {

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private AccountRepository accountRepository;

	@Mock
	private AccountService accountService;

	@Mock
	private BeneficiaryRepository beneficiaryRepository;

	@InjectMocks
	private TransactionService transactionService;

	private Account fromAccount;
	private Account toAccount;
	private Beneficiary beneficiary;
	private Transaction transaction;

	@BeforeEach
	public void setUp() {
		fromAccount = new Account();
		fromAccount.setSold(1000.0);
		fromAccount.setRib(123L);

		toAccount = new Account();
		toAccount.setSold(500.0);
		toAccount.setRib(456L);

		beneficiary = new Beneficiary();
		beneficiary.setIdB(1L);
		beneficiary.setName("Jane Doe");
		beneficiary.setRib(789L);
		beneficiary.setSold(200.0);

		transaction = new Transaction();
		transaction.setIdT(1L);
		transaction.setDate(LocalDateTime.now());
		transaction.setAmount(100.0);
		transaction.setDescription("Test Transaction");
		transaction.setFromAccount(fromAccount);
		transaction.setToAccount(toAccount);
		transaction.setTypeCard(TypeC.debit);
		transaction.setTypeT(TypeTransaction.internal);
	}

	@Test
	void testGetAllTransactions() {
		when(transactionRepository.findAll()).thenReturn(Arrays.asList(transaction));

		List<Transaction> result = transactionService.getAllTransactions();

		assertNotNull(result);
		assertEquals(1, result.size());
		verify(transactionRepository, times(1)).findAll();
	}

	@Test
	void testGetAccountSold() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));

		Double sold = transactionService.getAccountSold(1L);

		assertNotNull(sold);
		assertEquals(1000.0, sold);
		verify(accountRepository, times(1)).findById(1L);
	}

	@Test
	void testGetAccountSoldNotFound() {
		when(accountRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> transactionService.getAccountSold(1L));
		verify(accountRepository, times(1)).findById(1L);
	}



	@Test
	void testGetAccountTransactionsNotFound() {
		when(accountRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> transactionService.getAccountTransactions(1L));
		verify(accountRepository, times(1)).findById(1L);
	}

	@Test
	void testTransferMoney() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
		when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		String result = transactionService.transferMoney(1L, 2L, 100.0, "Transfer Description");

		assertEquals("Money transferred successfully", result);
		assertEquals(900.0, fromAccount.getSold());
		assertEquals(600.0, toAccount.getSold());
		verify(accountRepository, times(2)).findById(anyLong());
		verify(accountRepository, times(2)).save(any(Account.class));
		verify(transactionRepository, times(2)).save(any(Transaction.class));
	}

	@Test
	void testTransferMoneyInsufficientFunds() {
		fromAccount.setSold(50.0);
		when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
		when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

		assertThrows(IllegalStateException.class, () -> transactionService.transferMoney(1L, 2L, 100.0, "Transfer Description"));
		verify(accountRepository, times(1)).findById(1L);
		verify(accountRepository, times(1)).findById(2L);
	}

	@Test
	void testTransferMoneyFromAccountNotFound() {
		when(accountRepository.findById(1L)).thenReturn(Optional.empty());
		when(accountRepository.findById(2L)).thenReturn(Optional.of(toAccount));

		assertThrows(AccountNotFoundException.class, () -> transactionService.transferMoney(1L, 2L, 100.0, "Transfer Description"));
		verify(accountRepository, times(1)).findById(1L);
	}

	@Test
	void testTransferMoneyToAccountNotFound() {
		when(accountRepository.findById(1L)).thenReturn(Optional.of(fromAccount));
		when(accountRepository.findById(2L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> transactionService.transferMoney(1L, 2L, 100.0, "Transfer Description"));
		verify(accountRepository, times(1)).findById(1L);
		verify(accountRepository, times(1)).findById(2L);
	}

	@Test
	void testTransferExternally() {
		when(accountRepository.findByRib(123L)).thenReturn(Optional.of(fromAccount));
		when(beneficiaryRepository.findByRib(789L)).thenReturn(Optional.of(beneficiary));
		when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

		String result = transactionService.transferExternally(789L, 123L, 100.0, "External Transfer Description");

		assertEquals("External transfer to beneficiary completed successfully", result);
		assertEquals(900.0, fromAccount.getSold());
		assertEquals(300.0, beneficiary.getSold());
		verify(accountRepository, times(1)).findByRib(123L);
		verify(beneficiaryRepository, times(1)).findByRib(789L);
		verify(accountRepository, times(1)).save(fromAccount);
		verify(beneficiaryRepository, times(1)).save(beneficiary);
		verify(transactionRepository, times(2)).save(any(Transaction.class));
	}

	@Test
	void testTransferExternallyInsufficientFunds() {
		fromAccount.setSold(50.0);
		when(accountRepository.findByRib(123L)).thenReturn(Optional.of(fromAccount));
		when(beneficiaryRepository.findByRib(789L)).thenReturn(Optional.of(beneficiary));

		assertThrows(IllegalStateException.class, () -> transactionService.transferExternally(789L, 123L, 100.0, "External Transfer Description"));
		verify(accountRepository, times(1)).findByRib(123L);
		verify(beneficiaryRepository, times(1)).findByRib(789L);
	}

	@Test
	void testTransferExternallyAccountNotFound() {
		when(accountRepository.findByRib(123L)).thenReturn(Optional.empty());

		assertThrows(AccountNotFoundException.class, () -> transactionService.transferExternally(789L, 123L, 100.0, "External Transfer Description"));
		verify(accountRepository, times(1)).findByRib(123L);
	}

	@Test
	void testTransferExternallyBeneficiaryNotFound() {
		when(accountRepository.findByRib(123L)).thenReturn(Optional.of(fromAccount));
		when(beneficiaryRepository.findByRib(789L)).thenReturn(Optional.empty());

		assertThrows(BeneficiaryNotFoundException.class, () -> transactionService.transferExternally(789L, 123L, 100.0, "External Transfer Description"));
		verify(accountRepository, times(1)).findByRib(123L);
		verify(beneficiaryRepository, times(1)).findByRib(789L);
	}
}
