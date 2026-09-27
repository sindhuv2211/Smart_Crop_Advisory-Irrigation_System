package com.example.cropAnalysier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "crops")
public class crop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Crop name is required")
    @Column(nullable = false)
    private String cropName;

    @NotBlank(message = "Crop type is required")
    @Column(nullable = false)
    private String cropType;

    @NotBlank(message = "Crop season is required")
    @Column(nullable = false)
    private String cropSeason;

    @NotBlank(message = "Soil type is required")
    @Column(nullable = false)
    private String soilType;

    @NotBlank(message = "Water requirement is required")
    @Column(nullable = false)
    private String waterRequirement;

    public crop() {}

    public crop(String cropName, String cropType, String cropSeason, String soilType, String waterRequirement) {
        this.cropName = cropName;
        this.cropType = cropType;
        this.cropSeason = cropSeason;
        this.soilType = soilType;
        this.waterRequirement = waterRequirement;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCropName() { return cropName; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }
    public String getCropSeason() { return cropSeason; }
    public void setCropSeason(String cropSeason) { this.cropSeason = cropSeason; }
    public String getSoilType() { return soilType; }
    public void setSoilType(String soilType) { this.soilType = soilType; }
    public String getWaterRequirement() { return waterRequirement; }
    public void setWaterRequirement(String waterRequirement) { this.waterRequirement = waterRequirement; }
}
