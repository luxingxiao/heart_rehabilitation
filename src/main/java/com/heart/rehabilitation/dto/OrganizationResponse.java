package com.heart.rehabilitation.dto;

import com.heart.rehabilitation.model.OrganizationType;

import java.time.LocalDateTime;

public class OrganizationResponse {
    private Long id;
    private String name;
    private OrganizationType type;
    private String description;
    private String contactPhone;
    private String contactEmail;
    private String address;
    private Long parentOrganizationId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public OrganizationResponse() {}

    public OrganizationResponse(Long id, String name, OrganizationType type, String description,
                                String contactPhone, String contactEmail, String address,
                                Long parentOrganizationId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.address = address;
        this.parentOrganizationId = parentOrganizationId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public OrganizationType getType() { return type; }
    public void setType(OrganizationType type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Long getParentOrganizationId() { return parentOrganizationId; }
    public void setParentOrganizationId(Long parentOrganizationId) { this.parentOrganizationId = parentOrganizationId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}