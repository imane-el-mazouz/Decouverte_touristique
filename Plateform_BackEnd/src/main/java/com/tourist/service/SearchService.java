package com.tourist.service;

import com.tourist.dto.EventDTO;
import com.tourist.dto.ExcursionDTO;
import com.tourist.dto.HotelDTO;
import com.tourist.dto.SearchResultsDTO;
import com.tourist.enums.CategoryEvent;
import com.tourist.model.Event;
import com.tourist.model.Excursion;
import com.tourist.model.Hotel;
import com.tourist.repository.EventRepository;
import com.tourist.repository.ExcursionRepository;
import com.tourist.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final EventRepository eventRepository;
    private final ExcursionRepository excursionRepository;
    private final HotelRepository hotelRepository;

    public SearchService(EventRepository eventRepository, ExcursionRepository excursionRepository, HotelRepository hotelRepository) {
        this.eventRepository = eventRepository;
        this.excursionRepository = excursionRepository;
        this.hotelRepository = hotelRepository;
    }

    public SearchResultsDTO search(String location, LocalDate date, String category) {
        List<EventDTO> events = eventRepository.findEventByCategoryOrLocationOrDate(CategoryEvent.valueOf(category), location, date)
                .stream().map(this::convertEventToDTO).collect(Collectors.toList());

        List<ExcursionDTO> excursions = excursionRepository.findByDateTimeAndLocation(date.atStartOfDay(), location)
                .stream().map(this::convertExcursionToDTO).collect(Collectors.toList());

        List<HotelDTO> hotels = hotelRepository.findHotelByCategoryHotelOrLocation(null, location)
                .stream().map(this::convertHotelToDTO).collect(Collectors.toList());

        return new SearchResultsDTO(events, excursions, hotels);
    }

    private EventDTO convertEventToDTO(Event event) {
        return new EventDTO(event.getName(), event.getCategory(), event.getLocation(), event.getDate());
    }

    private ExcursionDTO convertExcursionToDTO(Excursion excursion) {
        return new ExcursionDTO(excursion.getName(), excursion.getLocation(), excursion.getDateTime());
    }

    private HotelDTO convertHotelToDTO(Hotel hotel) {
        return new HotelDTO(hotel.getName(), hotel.getCategoryHotel(), hotel.getLocation());
    }
}
