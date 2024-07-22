package com.tourist.controller;

import com.tourist.model.Card;
import com.tourist.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "http://localhost:4200")

public class CardController {
  @Autowired
  private CardService cardService;

  @GetMapping
  public ResponseEntity<List<Card>> getAllCards() {
    List<Card> cards = cardService.getAllCards();
    return ResponseEntity.ok(cards);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Card> getCardById(@PathVariable Long id) {
    Card card = cardService.getCardById(id);
    return ResponseEntity.ok(card);
  }

  @PostMapping("/save")
  public ResponseEntity<Card> saveCard(@RequestBody Card card) {
    Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
    if (loggedInUser == null || !loggedInUser.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    Card savedCard = cardService.saveCard(card);
    return ResponseEntity.ok(savedCard);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
    cardService.deleteCard(id);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateCard(@PathVariable Long id, @RequestBody Card card) {
    cardService.updateCard(id, card);
    return ResponseEntity.noContent().build();
  }

  @PutMapping("/{id}/status")
  public ResponseEntity<Void> updateCardStatus(@PathVariable Long id, @RequestBody Card card) {
    cardService.updateCard(id, card);
    return ResponseEntity.noContent().build();
  }
}
