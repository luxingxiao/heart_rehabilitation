package com.heart.rehabilitation.repository;

import com.heart.rehabilitation.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient> {
    Optional<Patient> findByMedicalRecordNumber(String medicalRecordNumber);
    Optional<Patient> findByUserId(Long userId);
    boolean existsByMedicalRecordNumber(String medicalRecordNumber);
}