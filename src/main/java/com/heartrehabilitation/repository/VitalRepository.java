package com.heartrehabilitation.repository;

import com.heartrehabilitation.entity.Vital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VitalRepository extends JpaRepository<Vital, Long> {
}