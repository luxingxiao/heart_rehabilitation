package com.heartrehabilitation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "assessments")
@Getter
@Setter
public class Assessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Double score; // numeric score if applicable

    @Column(columnDefinition = "TEXT")
    private String results; // JSON or textual results

    @Column(nullable = false)
    private LocalDateTime assessmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessed_by")
    private User assessedBy; // doctor who performed assessment

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Type {
        CPET,
        QUESTIONNAIRE,
        PHYSICAL_EXAM,
        LAB_TEST,
        OTHER
    }
}