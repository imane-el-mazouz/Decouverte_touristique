package com.tourist.service;

import com.tourist.exception.CardNotFoundException;
import com.tourist.model.Card;
import com.tourist.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card getCardById(Long id) {
        return cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
    }

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.findById(id).orElseThrow(CardNotFoundException::new);
        cardRepository.deleteById(id);
    }

//    public Card updateCard(Long id, Card updatedCard) {
//        Card existingCard = cardRepository.findById(id)
//                .orElseThrow(CardNotFoundException::new);
//        existingCard.setExpirationDate(updatedCard.getExpirationDate());
//        existingCard.setTypeCard(updatedCard.getTypeCard());
//        existingCard.setStatus(updatedCard.getStatus());
//        existingCard.setBlockingReason(updatedCard.getBlockingReason());
//        existingCard.setAccount(updatedCard.getAccount());
//
//        cardRepository.save(existingCard);
//        return existingCard;
//    }
public void updateCard(Long id, Card updatedCard) {
  Card existingCard = getCardById(id);
  existingCard.setStatus(updatedCard.getStatus());
  existingCard.setBlockingReason(updatedCard.getBlockingReason());
  cardRepository.save(existingCard);
}
}
