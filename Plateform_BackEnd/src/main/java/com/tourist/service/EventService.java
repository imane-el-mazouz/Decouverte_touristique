package com.tourist.service;

import com.tourist.dto.EventDTO;
import com.tourist.dto.EventFilterDTO;
import com.tourist.enums.CategoryEvent;
import com.tourist.exception.EventAlreadyExistsException;
import com.tourist.exception.EventNotFoundException;
import com.tourist.model.Event;
import com.tourist.repository.EventRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;
    private final Path rootLocation = Paths.get("uploaded-images");

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        File directory = new File(rootLocation.toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public Event saveEvent(EventDTO eventDTO) {
        System.out.println("EventDTO: " + eventDTO);
        System.out.println(eventDTO.getCategory());

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setImgPath(eventDTO.getImgPath());
        event.setDate(eventDTO.getDate());
        event.setLocation(eventDTO.getLocation());
        event.setCapacity(eventDTO.getCapacity());

        try {
            CategoryEvent category = CategoryEvent.valueOf(String.valueOf(eventDTO.getCategory()));
            event.setCategory(category);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Invalid category provided: " + eventDTO.getCategory());
        }

        event.setPrice(eventDTO.getPrice());
        event.setRating(eventDTO.getRating());
        event.setDistance(eventDTO.getDistance());

        return eventRepository.save(event);
    }


    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with ID: " + id));
        eventRepository.delete(event);
    }

    public Event updateEvent(Long id, EventDTO eventDTO) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + id));

        existingEvent.setName(eventDTO.getName());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setImgPath(eventDTO.getImgPath());
        existingEvent.setDate(eventDTO.getDate());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setCapacity(eventDTO.getCapacity());
        existingEvent.setPrice(eventDTO.getPrice());
        existingEvent.setRating(eventDTO.getRating());
        existingEvent.setDistance(eventDTO.getDistance());

        return eventRepository.save(existingEvent);
    }


    public List<Event> filterEvents(EventFilterDTO filterDTO) {
        return eventRepository.findAllByPriceBetweenAndRatingBetweenAndDistanceLessThan(
                filterDTO.getMinPrice(), filterDTO.getMaxPrice(),
                filterDTO.getMinRating(), filterDTO.getMaxRating(),
                filterDTO.getMaxDistance()
        );
    }

//    public List<Event> search(CategoryEvent category, String location, LocalDate date) {
//        return eventRepository.findEventByCategoryOrLocationOrDate(category, location, date);
//    }
public List<Event> searchEvents(String name, String category, Double maxDistance) {
    return eventRepository.findAll(
            Specification.where(hasName(name))
                    .and(hasCategory(category))
                    .and(isWithinDistance(maxDistance))
    );
}

    private Specification<Event> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                name != null ? criteriaBuilder.like(root.get("name"), "%" + name + "%") : criteriaBuilder.conjunction();
    }

    private Specification<Event> hasCategory(String category) {
        return (root, query, criteriaBuilder) ->
                category != null ? criteriaBuilder.equal(root.get("category"), category) : criteriaBuilder.conjunction();
    }

    private Specification<Event> isWithinDistance(Double maxDistance) {
        return (root, query, criteriaBuilder) ->
                maxDistance != null ? criteriaBuilder.lessThanOrEqualTo(root.get("distance"), maxDistance) : criteriaBuilder.conjunction();
    }

   private static final String UPLOADED_FOLDER = "src/main/resources/img/";
//
//    public String saveImage(MultipartFile file) throws IOException {
//        String fileName = file.getOriginalFilename();
//        File directory = new File(UPLOADED_FOLDER);
//        if (!directory.exists()) {
//            directory.mkdirs();
//        }
//
//        File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
//        file.transferTo(serverFile);
//        return "/img/" + fileName;
//    }
public String saveImage(MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    File directory = new File(UPLOADED_FOLDER);
    if (!directory.exists()) {
        directory.mkdirs();
    }

    File serverFile = new File(directory.getAbsolutePath() + File.separator + fileName);
    file.transferTo(serverFile);
    return "/img/" + fileName;
}

    public Event getEventById(Long id) {
        return eventRepository.findById(id) . orElseThrow(() -> new EventNotFoundException("not found"));
    }

    public List<Event> getAllEvents (){
        return eventRepository.findAll();
    }
}
