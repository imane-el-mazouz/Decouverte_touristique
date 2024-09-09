package com.tourist.controller;

import com.tourist.dto.EventDTO;
import com.tourist.dto.EventFilterDTO;
import com.tourist.dto.ReservationDTO;
import com.tourist.enums.CategoryEvent;
import com.tourist.exception.EventAlreadyExistsException;
import com.tourist.model.Event;
import com.tourist.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("api/event")
@CrossOrigin("*")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService){
        this.eventService = eventService;
    }

//    @PostMapping
//    @PreAuthorize("hasRole('Admin')")
//    public ResponseEntity<Event> saveEvent(
//            @RequestParam("name") String name,
//            @RequestParam("description") String description,
//            @RequestParam("date") LocalDate date,
//            @RequestParam("location") String location,
//            @RequestParam("capacity") Integer capacity,
//            @RequestParam("category") CategoryEvent category,
//            @RequestParam(value = "img", required = false) MultipartFile img,
//            @RequestParam(value = "reservations", required = false) List<ReservationDTO> reservations) throws IOException {
//
//        String imgPath = (img != null) ? eventService.saveImage(img) : null;
//        EventDTO eventDTO = new EventDTO(null, name, description, imgPath, date, location, capacity, category, reservations);
//
//        Event savedEvent = eventService.saveEvent(eventDTO)
//                .orElseThrow(() -> new EventAlreadyExistsException("Event already exists with this ID"));
//        return ResponseEntity.ok(savedEvent);
//    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Event> saveEvent(
            @RequestPart("event") EventDTO eventDTO,
            @RequestPart(value = "img", required = false) MultipartFile img) throws IOException {

        String imgPath = (img != null) ? eventService.saveImage(img) : null;
        eventDTO.setImgPath(imgPath);

        Event savedEvent = eventService.saveEvent(eventDTO)
                .orElseThrow(() -> new EventAlreadyExistsException("Event already exists with this ID"));

        return ResponseEntity.ok(savedEvent);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Event> updateEvent(
            @PathVariable Long id,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("date") LocalDate date,
            @RequestParam("location") String location,
            @RequestParam("capacity") Integer capacity,
            @RequestParam("category") CategoryEvent category,
            @RequestParam(value = "img", required = false) MultipartFile img,
            @RequestParam(value = "reservations", required = false) List<ReservationDTO> reservations) throws IOException {

        String imgPath = (img != null) ? eventService.saveImage(img) : null;
        EventDTO eventDTO = new EventDTO(id, name, description, imgPath, date, location, capacity, category, reservations);

        Event updatedEvent = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
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

    @GetMapping("/all")
    @PreAuthorize("hasRole('Client') or hasRole('Admin')")
    public ResponseEntity<List<Event>> getAllEvents(){
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

//    @PostMapping("/upload")
//    @PreAuthorize("hasRole('Admin')")
//
//    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
//        return eventService.saveImage(file);
//    }
@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
    try {
        String imageUrl = eventService.saveImage(file);
        return ResponseEntity.ok(Collections.singletonMap("imageUrl", imageUrl));
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap("error", "File upload failed"));
    }
}


}
