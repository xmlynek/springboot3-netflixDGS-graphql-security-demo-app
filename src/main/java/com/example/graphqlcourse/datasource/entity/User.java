package com.example.graphqlcourse.datasource.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "userz")
@Data
public class User {

    @Id
    private UUID id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String hashedPassword;
    private URL avatar;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    private String displayName;
    @Column(name = "active")
    private boolean isActive;
}
