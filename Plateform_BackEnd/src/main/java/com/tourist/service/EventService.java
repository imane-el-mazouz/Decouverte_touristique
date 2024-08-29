package com.tourist.service;

import com.tourist.dto.EventDTO;
import com.tourist.dto.EventFilterDTO;
import com.tourist.enums.CategoryEvent;
import com.tourist.exception.EventAlreadyExistsException;
import com.tourist.exception.EventNotFoundException;
import com.tourist.model.Event;
import com.tourist.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    private final EventRepository eventRepository;


    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public Optional<Event> saveEvent(EventDTO eventDTO) {
        if (eventRepository.existsById(eventDTO.getId())) {
            throw new EventAlreadyExistsException("Event already exists with this ID: " + eventDTO.getId());
        }

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setImg(eventDTO.getImg());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setCapacity(eventDTO.getCapacity());

        return Optional.of(eventRepository.save(event));
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
        eventRepository.delete(event);
    }

    public Event updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));

        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setImg(eventDTO.getImg());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setCapacity(eventDTO.getCapacity());

        return eventRepository.save(event);
    }

    public List<Event> filterEvents(EventFilterDTO filterDTO) {
        return eventRepository.findAllByPriceBetweenAndRatingBetweenAndDistanceLessThan(
                filterDTO.getMinPrice(), filterDTO.getMaxPrice(),
                filterDTO.getMinRating(), filterDTO.getMaxRating(),
                filterDTO.getMaxDistance()
        );
    }
    public List<Event> search(CategoryEvent category, String location, LocalDate date) {
        return eventRepository.findEventByCategoryOrLocationOrDate(category, location, LocalDate.from(date));
    }



}
