package com.github.italoalcantaraa.todolistapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Table(name = "tb_task")
@Entity
@Data
@NoArgsConstructor
public class Task {

    public Task(String description, User user) {
        this.description = description;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 200)
    private String description;

    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
