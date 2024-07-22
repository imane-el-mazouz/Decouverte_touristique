package com.tourist;

import com.tourist.enums.Bank;
import com.tourist.exception.AccountNotFoundException;
import com.tourist.model.Beneficiary;
import com.tourist.repository.BeneficiaryRepository;
import com.tourist.service.BeneficiaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BeneficiaryTest {

	@Mock
	private BeneficiaryRepository beneficiaryRepository;

	@InjectMocks
	private BeneficiaryService beneficiaryService;

	private Beneficiary beneficiary;

	@BeforeEach
	public void setUp() {
		beneficiary = new Beneficiary();
		beneficiary.setIdB(1L);
		beneficiary.setName("John Doe");
		beneficiary.setRib(Long.valueOf("1234567890"));
		beneficiary.setBank(Bank.valueOf("cih"));
		beneficiary.setAccount(null);
		beneficiary.setTransactions(null);
	}

	@Test
	void testGetAllBeneficiaries() {
		List<Beneficiary> beneficiaries = Collections.singletonList(beneficiary);
		when(beneficiaryRepository.findAll()).thenReturn(beneficiaries);
		List<Beneficiary> result = beneficiaryService.getAllBeneficiaries();
		assertNotNull(result);
		assertEquals(1, result.size());
		verify(beneficiaryRepository, times(1)).findAll();
	}

	@Test
	void testGetBeneficiaryById() {
		when(beneficiaryRepository.findById(1L)).thenReturn(Optional.of(beneficiary));
		Beneficiary result = beneficiaryService.getBeneficiaryById(1L);
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(beneficiaryRepository, times(1)).findById(1L);
	}

	@Test
	void testGetBeneficiaryByIdNotFound() {
		when(beneficiaryRepository.findById(1L)).thenReturn(Optional.empty());
		Beneficiary result = beneficiaryService.getBeneficiaryById(1L);
		assertNull(result);
		verify(beneficiaryRepository, times(1)).findById(1L);
	}

	@Test
	void testSaveBeneficiary() {
		when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);
		Beneficiary result = beneficiaryService.saveBeneficiary(beneficiary);
		assertNotNull(result);
		assertEquals("John Doe", result.getName());
		verify(beneficiaryRepository, times(1)).save(beneficiary);
	}

	@Test
	void testDeleteBeneficiary() {
		beneficiaryService.deleteBeneficiary(1L);
		verify(beneficiaryRepository, times(1)).deleteById(1L);
	}

	@Test
	void testUpdateBeneficiary() {
		Beneficiary updatedBeneficiary = new Beneficiary();
		updatedBeneficiary.setName("Jane Doe");
		updatedBeneficiary.setRib(Long.valueOf("0987654321"));
		updatedBeneficiary.setBank(Bank.valueOf("cih"));
		updatedBeneficiary.setAccount(null);
		updatedBeneficiary.setTransactions(null);

		when(beneficiaryRepository.findById(1L)).thenReturn(Optional.of(beneficiary));
		when(beneficiaryRepository.save(any(Beneficiary.class))).thenReturn(updatedBeneficiary);

		Beneficiary result = beneficiaryService.updateBeneficiary(1L, updatedBeneficiary);

		verify(beneficiaryRepository, times(1)).findById(1L);
		verify(beneficiaryRepository, times(1)).save(any(Beneficiary.class));

		assertNotNull(result);
		assertEquals("Jane Doe", result.getName());
		assertEquals(Long.valueOf("0987654321"), result.getRib());
		assertEquals(Bank.valueOf("cih"), result.getBank());
		assertNull(result.getAccount());
		assertNull(result.getTransactions());
	}


	@Test
	void testUpdateBeneficiaryNotFound() {
		when(beneficiaryRepository.findById(1L)).thenReturn(Optional.empty());
		Beneficiary updatedBeneficiary = new Beneficiary();
		updatedBeneficiary.setName("Jane Doe");
		updatedBeneficiary.setRib(Long.valueOf("0987654321"));
		updatedBeneficiary.setBank(Bank.valueOf("cih"));
		updatedBeneficiary.setAccount(null);
		updatedBeneficiary.setTransactions(null);

		assertThrows(AccountNotFoundException.class, () -> beneficiaryService.updateBeneficiary(1L, updatedBeneficiary));
		verify(beneficiaryRepository, times(1)).findById(1L);
		verify(beneficiaryRepository, times(0)).save(any(Beneficiary.class));
	}
}
