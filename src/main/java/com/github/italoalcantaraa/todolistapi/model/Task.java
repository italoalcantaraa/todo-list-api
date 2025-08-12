package com.github.italoalcantaraa.todolistapi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Table(name = "tb_task")
@Entity
@Data
public class Task {

    @Id
    private UUID id;

    @Column(nullable = false, length = 200)
    private String description;

    private boolean finished;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
