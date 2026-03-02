package com.heartrehabilitation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vitals")
@Getter
@Setter
public class Vital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private LocalDateTime measuredAt;

    // Cardiovascular
    private Integer heartRate; // bpm
    private Integer systolicBloodPressure; // mmHg
    private Integer diastolicBloodPressure; // mmHg

    // Body composition
    private Double weight; // kg
    private Double height; // cm

    // Oxygenation
    private Integer oxygenSaturation; // SpO2 %

    // Symptoms
    @Column(columnDefinition = "TEXT")
    private String symptoms;

    // Additional JSON data
    @Column(columnDefinition = "jsonb")
    private String extraData;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}