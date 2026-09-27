package com.example.cropAnalysier.service;

import com.example.cropAnalysier.model.farmer;
import com.example.cropAnalysier.repository.FarmerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FarmerService {
    private final FarmerRepository farmerRepository;
    private final PasswordEncoder passwordEncoder;

    public FarmerService(FarmerRepository farmerRepository, PasswordEncoder passwordEncoder) {
        this.farmerRepository = farmerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<farmer> getAllFarmers() {
        return farmerRepository.findAll();
    }

    public Optional<farmer> getFarmerById(Long id) {
        return farmerRepository.findById(id);
    }

    public farmer saveFarmer(farmer farmerRecord) {
        if (farmerRecord.getPassword() != null && !farmerRecord.getPassword().isBlank()) {
            farmerRecord.setPassword(passwordEncoder.encode(farmerRecord.getPassword()));
        }
        if (farmerRecord.getRole() == null || farmerRecord.getRole().isBlank()) {
            farmerRecord.setRole("ROLE_FARMER");
        }
        return farmerRepository.save(farmerRecord);
    }

    public farmer registerNewFarmer(farmer farmerRecord) {
        if (farmerRepository.findByEmail(farmerRecord.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }
        farmerRecord.setRole("ROLE_FARMER");
        return saveFarmer(farmerRecord);
    }

    public Optional<farmer> updateFarmer(Long id, farmer farmerDetails) {
        return farmerRepository.findById(id).map(existingFarmer -> {
            existingFarmer.setName(farmerDetails.getName());
            existingFarmer.setEmail(farmerDetails.getEmail());
            existingFarmer.setPhoneNumber(farmerDetails.getPhoneNumber());
            existingFarmer.setAddress(farmerDetails.getAddress());
            existingFarmer.setFarmLocation(farmerDetails.getFarmLocation());
            if (farmerDetails.getPassword() != null && !farmerDetails.getPassword().isBlank()) {
                existingFarmer.setPassword(passwordEncoder.encode(farmerDetails.getPassword()));
            }
            if (farmerDetails.getRole() != null && !farmerDetails.getRole().isBlank()) {
                existingFarmer.setRole(farmerDetails.getRole());
            }
            return farmerRepository.save(existingFarmer);
        });
    }

    public boolean deleteFarmer(Long id) {
        if (farmerRepository.existsById(id)) {
            farmerRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
