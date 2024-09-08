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

    private final EventRepository eventRepository;
    private final Path rootLocation = Paths.get("uploaded-images");

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        File directory = new File(rootLocation.toString());
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public Optional<Event> saveEvent(EventDTO eventDTO) {
        if (eventRepository.existsById(eventDTO.getId())) {
            throw new EventAlreadyExistsException("Event already exists with this ID: " + eventDTO.getId());
        }

        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setImgPath(eventDTO.getImgPath());
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
        event.setImgPath(eventDTO.getImgPath());
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
