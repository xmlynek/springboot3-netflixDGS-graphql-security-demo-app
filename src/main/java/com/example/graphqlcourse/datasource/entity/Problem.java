package com.example.graphqlcourse.datasource.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "problemz")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Problem {

    @Id
    private UUID id;
    @CreationTimestamp
    private LocalDateTime creationTimestamp;
    private String title;
    private String content;
    private String tags;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "problem")
    @OrderBy("creationTimestamp desc")
    private List<Solution> solutions = new ArrayList<>();
}
