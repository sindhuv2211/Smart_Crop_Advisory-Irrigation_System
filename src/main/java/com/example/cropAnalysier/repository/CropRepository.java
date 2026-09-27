package com.example.cropAnalysier.repository;

import com.example.cropAnalysier.model.crop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CropRepository extends JpaRepository<crop, Long> {
}
