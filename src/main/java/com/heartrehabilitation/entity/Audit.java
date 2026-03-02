package com.heartrehabilitation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audits")
@Getter
@Setter
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Action action;

    private String entityType; // e.g., "Patient", "Assessment"

    private Long entityId;

    @Column(columnDefinition = "TEXT")
    private String details;

    private String ipAddress;

    private String userAgent;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    public enum Action {
        CREATE,
        UPDATE,
        DELETE,
        VIEW,
        LOGIN,
        LOGOUT,
        EXPORT,
        IMPORT,
        OTHER
    }
}