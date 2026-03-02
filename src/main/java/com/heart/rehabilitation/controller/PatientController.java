package com.heart.rehabilitation.controller;

import com.heart.rehabilitation.dto.PatientRequest;
import com.heart.rehabilitation.dto.PatientResponse;
import com.heart.rehabilitation.model.Patient;
import com.heart.rehabilitation.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<PatientResponse> createPatient(@Valid @RequestBody PatientRequest request) {
        Patient patient = patientService.createPatient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toPatientResponse(patient));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<Page<PatientResponse>> getPatients(Pageable pageable,
                                                             @RequestParam(required = false) String search) {
        Page<Patient> patients = patientService.getPatients(pageable, search);
        Page<PatientResponse> responses = patients.map(this::toPatientResponse);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<PatientResponse> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getPatientById(id);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<PatientResponse> getPatientByUserId(@PathVariable Long userId) {
        Patient patient = patientService.getPatientByUserId(userId);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<PatientResponse> updatePatient(@PathVariable Long id,
                                                         @Valid @RequestBody PatientRequest request) {
        Patient patient = patientService.updatePatient(id, request);
        return ResponseEntity.ok(toPatientResponse(patient));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

    private PatientResponse toPatientResponse(Patient patient) {
        return new PatientResponse(
                patient.getId(),
                patient.getUser().getId(),
                patient.getUser().getUsername(),
                patient.getUser().getEmail(),
                patient.getUser().getFirstName(),
                patient.getUser().getLastName(),
                patient.getUser().getPhoneNumber(),
                patient.getUser().getOrganization().getId(),
                patient.getUser().getOrganization().getName(),
                patient.getMedicalRecordNumber(),
                patient.getDateOfBirth(),
                patient.getGender(),
                patient.getHeight(),
                patient.getWeight(),
                patient.getBloodType(),
                patient.getPrimaryDiagnosis(),
                patient.getDiagnosisDate(),
                patient.getAllergies(),
                patient.getMedications(),
                patient.getCreatedAt(),
                patient.getUpdatedAt()
        );
    }
}