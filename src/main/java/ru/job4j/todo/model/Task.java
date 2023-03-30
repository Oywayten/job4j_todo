package ru.job4j.todo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

/**
 * Oywayten 15.03.2023.
 */
@NamedEntityGraph(
        name = "task-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("priority"),
                @NamedAttributeNode("categories"),
        }
)
@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    private String description;

    private LocalDateTime created = LocalDateTime.now();

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "todo_user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private Priority priority;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_category",
            joinColumns = {@JoinColumn(name = "task_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> categories = new ArrayList<>();

    public String createdWithTimezone(String timezone) {
        return created.atZone(ZoneId.of(TimeZone.getDefault().getID()))
                .withZoneSameInstant(ZoneId.of(timezone))
                .format(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"));
    }


}
