package com.example.cropAnalysier.service;

import com.example.cropAnalysier.model.crop;
import com.example.cropAnalysier.repository.CropRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CropService {
    private final CropRepository cropRepository;

    public CropService(CropRepository cropRepository) {
        this.cropRepository = cropRepository;
    }

    public List<crop> getAllCrops() {
        return cropRepository.findAll();
    }

    public Optional<crop> getCropById(Long id) {
        return cropRepository.findById(id);
    }

    public crop saveCrop(crop cropRecord) {
        return cropRepository.save(cropRecord);
    }

    public Optional<crop> updateCrop(Long id, crop cropDetails) {
        return cropRepository.findById(id).map(existingCrop -> {
            existingCrop.setCropName(cropDetails.getCropName());
            existingCrop.setCropType(cropDetails.getCropType());
            existingCrop.setCropSeason(cropDetails.getCropSeason());
            existingCrop.setSoilType(cropDetails.getSoilType());
            existingCrop.setWaterRequirement(cropDetails.getWaterRequirement());
            return cropRepository.save(existingCrop);
        });
    }

    public boolean deleteCrop(Long id) {
        if (cropRepository.existsById(id)) {
            cropRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Map<String, Object>> getCropAdvisorySummary() {
        List<Map<String, Object>> advisories = new ArrayList<>();
        for (crop cropRecord : cropRepository.findAll()) {
            Map<String, Object> advice = new HashMap<>();
            advice.put("cropName", cropRecord.getCropName());
            advice.put("soilType", cropRecord.getSoilType());
            advice.put("cropSeason", cropRecord.getCropSeason());
            advice.put("waterRequirement", cropRecord.getWaterRequirement());
            advice.put("irrigationGuidance", getIrrigationGuidance(cropRecord.getWaterRequirement()));
            advice.put("generalAdvice", getGeneralAdvisory(cropRecord.getCropName()));
            advisories.add(advice);
        }
        return advisories;
    }

    private String getIrrigationGuidance(String waterRequirement) {
        if (waterRequirement == null) {
            return "Use balanced irrigation scheduling.";
        }
        String requirement = waterRequirement.toLowerCase();
        if (requirement.contains("high")) {
            return "Requires regular irrigation and checks for water stress.";
        }
        if (requirement.contains("moderate")) {
            return "Maintain moderate soil moisture and avoid waterlogging.";
        }
        return "Avoid excessive irrigation and monitor moisture levels.";
    }

    private String getGeneralAdvisory(String cropName) {
        if (cropName == null) {
            return "Use crop rotation and monitor field conditions.";
        }
        String name = cropName.toLowerCase();
        if (name.contains("rice")) {
            return "Rice requires frequent water, but drainage should be managed well.";
        }
        if (name.contains("wheat")) {
            return "Wheat performs best with moderate moisture and timely nutrient support.";
        }
        if (name.contains("millet")) {
            return "Millet prefers well-drained soil and low to moderate irrigation.";
        }
        return "Maintain soil quality, monitor pest pressure, and irrigate according to crop stage.";
    }
}
