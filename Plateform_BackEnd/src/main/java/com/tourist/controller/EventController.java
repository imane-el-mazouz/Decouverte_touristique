package com.tourist.controller;

import com.tourist.dto.EventDTO;
import com.tourist.dto.EventFilterDTO;
import com.tourist.enums.CategoryEvent;
import com.tourist.exception.EventAlreadyExistsException;
import com.tourist.model.Event;
import com.tourist.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/event")
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;

   public EventController(EventService eventService){

       this.eventService = eventService;
   }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Event> saveEvent(@RequestBody EventDTO eventDTO) {
        Event savedEvent = eventService.saveEvent(eventDTO)
                .orElseThrow(() -> new EventAlreadyExistsException("Event already exists with this ID"));
        return ResponseEntity.ok(savedEvent);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> getEventById(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        Event updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')")
    public ResponseEntity<List<Event>> filterEvents(EventFilterDTO filterDTO) {
        List<Event> filteredEvents = eventService.filterEvents(filterDTO);
        return ResponseEntity.ok(filteredEvents);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')")
    public ResponseEntity<List<Event>> searchEvents(
            @RequestParam(required = false) CategoryEvent category,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) LocalDate date) {
        List<Event> eventList = eventService.search(category, location, date);
        return ResponseEntity.ok(eventList);
    }



}
