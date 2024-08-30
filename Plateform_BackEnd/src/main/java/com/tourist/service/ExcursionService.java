package com.tourist.service;

import com.tourist.dto.ExcursionDTO;
import com.tourist.model.Excursion;
import com.tourist.repository.ExcursionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExcursionService {

    @Autowired
    private ExcursionRepository excursionRepository;

    private static final String IMAGE_DIRECTORY = "/path/to/your/image/directory";

    public Excursion saveExcursion(ExcursionDTO excursionDTO) throws IOException {
        MultipartFile imgFile = excursionDTO.getImg();
        String imgPath = saveImage(imgFile);

        Excursion excursion = new Excursion();
        excursion.setName(excursionDTO.getName());
        excursion.setDescription(excursionDTO.getDescription());
        excursion.setImgPath(imgPath);  // Save the image path
        excursion.setDateTime(excursionDTO.getDateTime());
        excursion.setLocation(excursionDTO.getLocation());
        excursion.setCapacity(excursionDTO.getCapacity());

        return excursionRepository.save(excursion);
    }

    private String saveImage(MultipartFile imgFile) throws IOException {
        if (imgFile == null || imgFile.isEmpty()) {
            return null;
        }

        String imgName = System.currentTimeMillis() + "_" + imgFile.getOriginalFilename();
        Path path = Paths.get(IMAGE_DIRECTORY, imgName);
        Files.write(path, imgFile.getBytes());

        return imgName;
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

    public Excursion updateExcursion(Long id, Excursion updatedExcursion) {
        Excursion excursion = getExcursionById(id);

        excursion.setName(updatedExcursion.getName());
        excursion.setDescription(updatedExcursion.getDescription());
        excursion.setImgPath(updatedExcursion.getImgPath());
        excursion.setDateTime(updatedExcursion.getDateTime());
        excursion.setLocation(updatedExcursion.getLocation());
        excursion.setCapacity(updatedExcursion.getCapacity());

        return excursionRepository.save(excursion);
    }
    public void deleteExcursion(Long id) {
        Excursion excursion = getExcursionById(id);
        excursionRepository.delete(excursion);
    }



}
