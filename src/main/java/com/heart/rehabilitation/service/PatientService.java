package com.heart.rehabilitation.service;

import com.heart.rehabilitation.dto.PatientRequest;
import com.heart.rehabilitation.model.Patient;
import com.heart.rehabilitation.model.User;
import com.heart.rehabilitation.repository.PatientRepository;
import com.heart.rehabilitation.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;

    public PatientService(PatientRepository patientRepository, UserRepository userRepository,
                         CurrentUserService currentUserService) {
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.currentUserService = currentUserService;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    @Transactional
    public Patient createPatient(PatientRequest request) {
        // Check if medical record number already exists
        if (patientRepository.existsByMedicalRecordNumber(request.getMedicalRecordNumber())) {
            throw new RuntimeException("Medical record number already exists");
        }
        // Fetch user
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        // Ensure user is not already linked to another patient
        Optional<Patient> existingPatient = patientRepository.findByUserId(user.getId());
        if (existingPatient.isPresent()) {
            throw new RuntimeException("User is already associated with a patient");
        }
        // Create patient
        Patient patient = new Patient();
        patient.setUser(user);
        patient.setMedicalRecordNumber(request.getMedicalRecordNumber());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patient.setBloodType(request.getBloodType());
        patient.setPrimaryDiagnosis(request.getPrimaryDiagnosis());
        patient.setDiagnosisDate(request.getDiagnosisDate());
        patient.setAllergies(request.getAllergies());
        patient.setMedications(request.getMedications());
        return patientRepository.save(patient);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public Page<Patient> getPatients(Pageable pageable, String search) {
        // Simple search by medical record number or user's first/last name
        if (search != null && !search.trim().isEmpty()) {
            // For simplicity, search by medical record number or user's first/last name
            // This could be enhanced with JPQL or Specifications
            return patientRepository.findAll((root, query, cb) -> {
                String pattern = "%" + search.toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(root.get("medicalRecordNumber")), pattern),
                        cb.like(cb.lower(root.get("user").get("firstName")), pattern),
                        cb.like(cb.lower(root.get("user").get("lastName")), pattern)
                );
            }, pageable);
        }
        return patientRepository.findAll(pageable);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public Patient getPatientByUserId(Long userId) {
        return patientRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found for user"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    @Transactional
    public Patient updatePatient(Long id, PatientRequest request) {
        Patient patient = getPatientById(id);
        // If medical record number changed, check uniqueness
        if (!patient.getMedicalRecordNumber().equals(request.getMedicalRecordNumber()) &&
                patientRepository.existsByMedicalRecordNumber(request.getMedicalRecordNumber())) {
            throw new RuntimeException("Medical record number already exists");
        }
        patient.setMedicalRecordNumber(request.getMedicalRecordNumber());
        patient.setDateOfBirth(request.getDateOfBirth());
        patient.setGender(request.getGender());
        patient.setHeight(request.getHeight());
        patient.setWeight(request.getWeight());
        patient.setBloodType(request.getBloodType());
        patient.setPrimaryDiagnosis(request.getPrimaryDiagnosis());
        patient.setDiagnosisDate(request.getDiagnosisDate());
        patient.setAllergies(request.getAllergies());
        patient.setMedications(request.getMedications());
        // User cannot be changed
        return patientRepository.save(patient);
    }

    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN')")
    @Transactional
    public void deletePatient(Long id) {
        Patient patient = getPatientById(id);
        // Check if there are associated records (e.g., assessments, prescriptions) - to be added later
        patientRepository.delete(patient);
    }
}