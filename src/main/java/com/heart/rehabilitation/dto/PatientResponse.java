package com.heart.rehabilitation.dto;

import com.heart.rehabilitation.model.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class PatientResponse {
    private Long id;
    private Long userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Long organizationId;
    private String organizationName;
    private String medicalRecordNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private Double height;
    private Double weight;
    private String bloodType;
    private String primaryDiagnosis;
    private LocalDate diagnosisDate;
    private String allergies;
    private String medications;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PatientResponse() {}

    public PatientResponse(Long id, Long userId, String username, String email, String firstName, String lastName,
                          String phoneNumber, Long organizationId, String organizationName,
                          String medicalRecordNumber, LocalDate dateOfBirth, Gender gender, Double height,
                          Double weight, String bloodType, String primaryDiagnosis, LocalDate diagnosisDate,
                          String allergies, String medications, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.organizationId = organizationId;
        this.organizationName = organizationName;
        this.medicalRecordNumber = medicalRecordNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.bloodType = bloodType;
        this.primaryDiagnosis = primaryDiagnosis;
        this.diagnosisDate = diagnosisDate;
        this.allergies = allergies;
        this.medications = medications;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public Long getOrganizationId() { return organizationId; }
    public void setOrganizationId(Long organizationId) { this.organizationId = organizationId; }
    public String getOrganizationName() { return organizationName; }
    public void setOrganizationName(String organizationName) { this.organizationName = organizationName; }
    public String getMedicalRecordNumber() { return medicalRecordNumber; }
    public void setMedicalRecordNumber(String medicalRecordNumber) { this.medicalRecordNumber = medicalRecordNumber; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public Double getHeight() { return height; }
    public void setHeight(Double height) { this.height = height; }
    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public String getPrimaryDiagnosis() { return primaryDiagnosis; }
    public void setPrimaryDiagnosis(String primaryDiagnosis) { this.primaryDiagnosis = primaryDiagnosis; }
    public LocalDate getDiagnosisDate() { return diagnosisDate; }
    public void setDiagnosisDate(LocalDate diagnosisDate) { this.diagnosisDate = diagnosisDate; }
    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
    public String getMedications() { return medications; }
    public void setMedications(String medications) { this.medications = medications; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}