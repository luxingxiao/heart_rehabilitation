package com.heart.rehabilitation.service;

import com.heart.rehabilitation.dto.OrganizationRequest;
import com.heart.rehabilitation.model.Organization;
import com.heart.rehabilitation.model.OrganizationType;
import com.heart.rehabilitation.repository.OrganizationRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    public OrganizationService(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Organization createOrganization(OrganizationRequest request) {
        Organization organization = new Organization();
        organization.setName(request.getName());
        organization.setType(request.getType());
        organization.setDescription(request.getDescription());
        organization.setContactPhone(request.getContactPhone());
        organization.setContactEmail(request.getContactEmail());
        organization.setAddress(request.getAddress());
        if (request.getParentOrganizationId() != null) {
            Organization parent = organizationRepository.findById(request.getParentOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Parent organization not found"));
            organization.setParentOrganization(parent);
        }
        return organizationRepository.save(organization);
    }

    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    @Transactional
    public Organization updateOrganization(Long id, OrganizationRequest request) {
        Organization organization = getOrganizationById(id);
        organization.setName(request.getName());
        organization.setType(request.getType());
        organization.setDescription(request.getDescription());
        organization.setContactPhone(request.getContactPhone());
        organization.setContactEmail(request.getContactEmail());
        organization.setAddress(request.getAddress());
        if (request.getParentOrganizationId() != null) {
            Organization parent = organizationRepository.findById(request.getParentOrganizationId())
                    .orElseThrow(() -> new RuntimeException("Parent organization not found"));
            organization.setParentOrganization(parent);
        } else {
            organization.setParentOrganization(null);
        }
        return organization;
    }

    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteOrganization(Long id) {
        Organization organization = getOrganizationById(id);
        // Check if there are users associated
        // We'll add later
        organizationRepository.delete(organization);
    }

    public List<Organization> getOrganizationsByType(OrganizationType type) {
        return organizationRepository.findByType(type);
    }
}