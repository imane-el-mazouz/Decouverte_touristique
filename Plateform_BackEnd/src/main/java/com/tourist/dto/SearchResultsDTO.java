package com.tourist.dto;

import java.util.List;

public class SearchResultsDTO {
    private List<EventDTO> events;
    private List<ExcursionDTO> excursions;
    private List<HotelDTO> hotels;

    public SearchResultsDTO(List<EventDTO> events, List<ExcursionDTO> excursions, List<HotelDTO> hotels) {
        this.events = events;
        this.excursions = excursions;
        this.hotels = hotels;
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    public List<ExcursionDTO> getExcursions() {
        return excursions;
    }

    public List<HotelDTO> getHotels() {
        return hotels;
    }
}
