package com.heart.rehabilitation.controller;

import com.heart.rehabilitation.dto.OrganizationRequest;
import com.heart.rehabilitation.dto.OrganizationResponse;
import com.heart.rehabilitation.model.Organization;
import com.heart.rehabilitation.model.OrganizationType;
import com.heart.rehabilitation.service.OrganizationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<OrganizationResponse> createOrganization(@Valid @RequestBody OrganizationRequest request) {
        Organization organization = organizationService.createOrganization(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(toOrganizationResponse(organization));
    }

    @GetMapping
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<List<OrganizationResponse>> getAllOrganizations() {
        List<Organization> organizations = organizationService.getAllOrganizations();
        List<OrganizationResponse> responses = organizations.stream()
                .map(this::toOrganizationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<OrganizationResponse> getOrganizationById(@PathVariable Long id) {
        Organization organization = organizationService.getOrganizationById(id);
        return ResponseEntity.ok(toOrganizationResponse(organization));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<OrganizationResponse> updateOrganization(@PathVariable Long id,
                                                                   @Valid @RequestBody OrganizationRequest request) {
        Organization organization = organizationService.updateOrganization(id, request);
        return ResponseEntity.ok(toOrganizationResponse(organization));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<Void> deleteOrganization(@PathVariable Long id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    @PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('HOSPITAL_ADMIN') or hasRole('COMMUNITY_ADMIN')")
    public ResponseEntity<List<OrganizationResponse>> getOrganizationsByType(@PathVariable OrganizationType type) {
        List<Organization> organizations = organizationService.getOrganizationsByType(type);
        List<OrganizationResponse> responses = organizations.stream()
                .map(this::toOrganizationResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    private OrganizationResponse toOrganizationResponse(Organization organization) {
        return new OrganizationResponse(
                organization.getId(),
                organization.getName(),
                organization.getType(),
                organization.getDescription(),
                organization.getContactPhone(),
                organization.getContactEmail(),
                organization.getAddress(),
                organization.getParentOrganization() != null ? organization.getParentOrganization().getId() : null,
                organization.getCreatedAt(),
                organization.getUpdatedAt()
        );
    }
}