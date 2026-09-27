package com.example.cropAnalysier.service;

import com.example.cropAnalysier.model.Irregation;
import com.example.cropAnalysier.repository.IrregationRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class IrrigationService {
    private final IrregationRepository irregationRepository;

    public IrrigationService(IrregationRepository irregationRepository) {
        this.irregationRepository = irregationRepository;
    }

    public List<Irregation> getAllIrrigations() {
        return irregationRepository.findAll().stream()
                .sorted(Comparator.comparing(Irregation::getIrrigationTime).reversed())
                .toList();
    }

    public Optional<Irregation> getIrrigationById(Long id) {
        return irregationRepository.findById(id);
    }

    public Optional<Irregation> getLatestIrrigation() {
        return irregationRepository.findTopByOrderByIrrigationTimeDesc();
    }

    public Irregation saveIrrigation(Irregation irrigationRecord) {
        if (irrigationRecord.getSoilMoisture() != null) {
            irrigationRecord.setStatus(determineStatus(irrigationRecord.getSoilMoisture()));
        }
        return irregationRepository.save(irrigationRecord);
    }

    public Optional<Irregation> updateIrrigation(Long id, Irregation irrigationDetails) {
        return irregationRepository.findById(id).map(existingIrrigation -> {
            existingIrrigation.setCrop(irrigationDetails.getCrop());
            existingIrrigation.setSoilMoisture(irrigationDetails.getSoilMoisture());
            existingIrrigation.setWaterQuantity(irrigationDetails.getWaterQuantity());
            existingIrrigation.setIrrigationTime(irrigationDetails.getIrrigationTime());
            existingIrrigation.setIrrigationType(irrigationDetails.getIrrigationType());
            existingIrrigation.setStatus(determineStatus(irrigationDetails.getSoilMoisture()));
            return irregationRepository.save(existingIrrigation);
        });
    }

    public boolean deleteIrrigation(Long id) {
        if (irregationRepository.existsById(id)) {
            irregationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public String determineStatus(Double soilMoisture) {
        if (soilMoisture == null) {
            return "MONITOR";
        }
        if (soilMoisture < 20) {
            return "IRRIGATION REQUIRED";
        }
        if (soilMoisture <= 40) {
            return "MONITOR";
        }
        return "SUFFICIENT";
    }
}
