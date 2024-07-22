//package com.bank;
//
//import com.bank.enums.Reason;
//import com.bank.enums.Status;
//import com.bank.enums.TypeC;
//import com.bank.exception.CardNotFoundException;
//import com.bank.model.Card;
//import com.bank.repository.CardRepository;
//import com.bank.service.CardService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.Optional;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class CardTest {
//
//	@Mock
//	private CardRepository cardRepository;
//
//	@InjectMocks
//	private CardService cardService;
//
//	private Card card;
//
//	@BeforeEach
//	public void setUp() {
//		card = new Card();
//		card.setIdC(1L);
//		card.setExpirationDate(LocalDateTime.parse("2027-07-22T00:00"));
//		card.setTypeCard(TypeC.credit);
//		card.setStatus(Status.activated);
//		card.setBlockingReason(Reason.none);
//		card.setAccount(null);
//	}
//
//	@Test
//	void testGetAllCards() {
//		List<Card> cards = Collections.singletonList(card);
//		when(cardRepository.findAll()).thenReturn(cards);
//		List<Card> result = cardService.getAllCards();
//		assertNotNull(result);
//		assertEquals(1, result.size());
//		verify(cardRepository, times(1)).findAll();
//	}
//
//	@Test
//	void testGetCardById() {
//		when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
//		Card result = cardService.getCardById(1L);
//		assertNotNull(result);
//		assertEquals(LocalDateTime.parse("2027-07-22T00:00"), result.getExpirationDate());
//		verify(cardRepository, times(1)).findById(1L);
//	}
//
//	@Test
//	void testGetCardByIdNotFound() {
//		when(cardRepository.findById(1L)).thenReturn(Optional.empty());
//		assertThrows(CardNotFoundException.class, () -> cardService.getCardById(1L));
//		verify(cardRepository, times(1)).findById(1L);
//	}
//
//	@Test
//	void testSaveCard() {
//		when(cardRepository.save(card)).thenReturn(card);
//
//		Card result = cardService.saveCard(card);
//
//		assertNotNull(result);
//		assertEquals(LocalDateTime.parse("2027-07-22T00:00"), result.getExpirationDate());
//		verify(cardRepository, times(1)).save(card);
//	}
//
//	@Test
//	void testDeleteCard() {
//		when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
//
//		cardService.deleteCard(1L);
//
//		verify(cardRepository, times(1)).findById(1L);
//		verify(cardRepository, times(1)).deleteById(1L);
//	}
//
//	@Test
//	void testDeleteCardNotFound() {
//		when(cardRepository.findById(1L)).thenReturn(Optional.empty());
//
//		assertThrows(CardNotFoundException.class, () -> cardService.deleteCard(1L));
//		verify(cardRepository, times(1)).findById(1L);
//		verify(cardRepository, times(0)).deleteById(1L);
//	}
//
//	@Test
//	void testUpdateCard() {
//		Card updatedCard = new Card();
//		updatedCard.setExpirationDate(LocalDateTime.parse("2028-01-26T00:00"));
//		updatedCard.setTypeCard(TypeC.debit);
//		updatedCard.setStatus(Status.desactivated);
//		updatedCard.setBlockingReason(Reason.loss);
//		updatedCard.setAccount(null);
//
//		when(cardRepository.findById(1L)).thenReturn(Optional.of(card));
//		when(cardRepository.save(any(Card.class))).thenReturn(updatedCard);
//
//		Card result = cardService.updateCard(1L, updatedCard);
//
//		verify(cardRepository, times(1)).findById(1L);
//		verify(cardRepository, times(1)).save(any(Card.class));
//
//		assertNotNull(result);
//		assertEquals(LocalDateTime.parse("2028-01-26T00:00"), result.getExpirationDate());
//		assertEquals(TypeC.debit, result.getTypeCard());
//		assertEquals(Status.desactivated, result.getStatus());
//		assertEquals(Reason.loss, result.getBlockingReason());
//		assertNull(result.getAccount());
//	}
//
//	@Test
//	void testUpdateCardNotFound() {
//		when(cardRepository.findById(1L)).thenReturn(Optional.empty());
//
//		Card updatedCard = new Card();
//		updatedCard.setExpirationDate(LocalDateTime.parse("2028-01-26T00:00"));
//		updatedCard.setTypeCard(TypeC.debit);
//		updatedCard.setStatus(Status.desactivated);
//		updatedCard.setBlockingReason(Reason.loss);
//		updatedCard.setAccount(null);
//
//		assertThrows(CardNotFoundException.class, () -> cardService.updateCard(1L, updatedCard));
//		verify(cardRepository, times(1)).findById(1L);
//		verify(cardRepository, times(0)).save(any(Card.class));
//	}
//}
