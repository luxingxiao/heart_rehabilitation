package com.heart.rehabilitation.repository;

import com.heart.rehabilitation.model.Organization;
import com.heart.rehabilitation.model.OrganizationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    List<Organization> findByType(OrganizationType type);
    List<Organization> findByParentOrganizationId(Long parentId);
}