package com.tourist.service;

import com.tourist.dto.ExcursionDTO;
import com.tourist.model.Excursion;
import com.tourist.repository.ExcursionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExcursionService {

    @Autowired
    private ExcursionRepository excursionRepository;

    public Excursion saveExcursion(ExcursionDTO excursionDTO) {
        Excursion excursion = new Excursion();
        excursion.setName(excursionDTO.getName());
        excursion.setDescription(excursionDTO.getDescription());
        excursion.setImgPath(excursionDTO.getImgPath());
        excursion.setDateTime(excursionDTO.getDateTime());
        excursion.setLocation(excursionDTO.getLocation());
        excursion.setCapacity(excursionDTO.getCapacity());
        excursion.setRating(excursionDTO.getRating());

        return excursionRepository.save(excursion);
    }


    public List<Excursion> getAllExcursions() {
        return excursionRepository.findAll();
    }

    public List<Excursion> searchExcursions(LocalDateTime date, String location) {
        if (date != null && location != null) {
            return excursionRepository.findByDateTimeAndLocation(date, location);
        } else if (date != null) {
            return excursionRepository.findByDateTime(date);
        } else if (location != null) {
            return excursionRepository.findByLocation(location);
        } else {
            return getAllExcursions();
        }
    }

    public List<Excursion> filterExcursionsByRating(Long minRating, Long maxRating) {
        return excursionRepository.findByRatingBetween(minRating, maxRating);
    }

    public Excursion getExcursionById(Long id) {
        return excursionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Excursion not found with id: " + id));
    }

    public Excursion updateExcursion(Long id, ExcursionDTO excursionDTO) {
        Excursion existingExcursion = excursionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Excursion not found with id: " + id));

        if (excursionDTO.getName() == null || excursionDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Excursion name cannot be null or empty.");
        }
        if (excursionDTO.getDescription() == null || excursionDTO.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Excursion description cannot be null or empty.");
        }
        if (excursionDTO.getDateTime() == null) {
            throw new IllegalArgumentException("Excursion date and time cannot be null.");
        }
        if (excursionDTO.getLocation() == null || excursionDTO.getLocation().isEmpty()) {
            throw new IllegalArgumentException("Excursion location cannot be null or empty.");
        }
        if (excursionDTO.getCapacity() <= 0) {
            throw new IllegalArgumentException("Excursion capacity must be greater than zero.");
        }
        if (excursionDTO.getRating() < 0 || excursionDTO.getRating() > 5) {
            throw new IllegalArgumentException("Excursion rating must be between 0 and 5.");
        }
        existingExcursion.setName(excursionDTO.getName());
        existingExcursion.setDescription(excursionDTO.getDescription());
        existingExcursion.setImgPath(excursionDTO.getImgPath());
        existingExcursion.setDateTime(excursionDTO.getDateTime());
        existingExcursion.setLocation(excursionDTO.getLocation());
        existingExcursion.setCapacity(excursionDTO.getCapacity());
        existingExcursion.setRating(excursionDTO.getRating());

        return excursionRepository.save(existingExcursion);
    }


    public void deleteExcursion(Long id) {
        Excursion excursion = getExcursionById(id);
        excursionRepository.delete(excursion);
    }




}


