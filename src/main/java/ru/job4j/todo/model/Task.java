package ru.job4j.todo.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Oywayten 15.03.2023.
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"description", "created"}, name = "description_created")})
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(nullable = false)
    private String description;

    @CreationTimestamp
    @NotNull
    @Column(updatable = false, nullable = false, columnDefinition = "TIMESTAMP NOT NULL DEFAULT current_timestamp")
    private LocalDateTime created;

    @NotNull
    @Column(nullable = false, columnDefinition = "BOOLEAN NOT NULL DEFAULT FALSE")
    private boolean done;
}
