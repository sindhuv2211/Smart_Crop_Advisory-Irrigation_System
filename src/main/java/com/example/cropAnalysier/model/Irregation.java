package com.example.cropAnalysier.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "irrigations")
public class Irregation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crop_id", nullable = false)
    private crop crop;

    @NotNull(message = "Soil moisture is required")
    @DecimalMin(value = "0.0", message = "Soil moisture must be at least 0")
    @DecimalMax(value = "100.0", message = "Soil moisture must be less than or equal to 100")
    @Column(nullable = false)
    private Double soilMoisture;

    @NotNull(message = "Water quantity is required")
    @Column(nullable = false)
    private Double waterQuantity;

    @NotNull(message = "Irrigation time is required")
    @Column(nullable = false)
    private LocalDateTime irrigationTime;

    @NotBlank(message = "Irrigation type is required")
    @Column(nullable = false)
    private String irrigationType;

    @Column(nullable = false)
    private String status = "MONITOR";

    public Irregation() {}

    public Irregation(crop crop, Double soilMoisture, Double waterQuantity, LocalDateTime irrigationTime, String irrigationType, String status) {
        this.crop = crop;
        this.soilMoisture = soilMoisture;
        this.waterQuantity = waterQuantity;
        this.irrigationTime = irrigationTime;
        this.irrigationType = irrigationType;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public crop getCrop() { return crop; }
    public void setCrop(crop crop) { this.crop = crop; }
    public Double getSoilMoisture() { return soilMoisture; }
    public void setSoilMoisture(Double soilMoisture) { this.soilMoisture = soilMoisture; }
    public Double getWaterQuantity() { return waterQuantity; }
    public void setWaterQuantity(Double waterQuantity) { this.waterQuantity = waterQuantity; }
    public LocalDateTime getIrrigationTime() { return irrigationTime; }
    public void setIrrigationTime(LocalDateTime irrigationTime) { this.irrigationTime = irrigationTime; }
    public String getIrrigationType() { return irrigationType; }
    public void setIrrigationType(String irrigationType) { this.irrigationType = irrigationType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
