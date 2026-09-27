package com.example.cropAnalysier.repository;

import com.example.cropAnalysier.model.farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FarmerRepository extends JpaRepository<farmer, Long> {
    Optional<farmer> findByEmail(String email);
}
