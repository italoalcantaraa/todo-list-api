package com.github.italoalcantaraa.todolistapi.repository;

import com.github.italoalcantaraa.todolistapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByUsername(String username);
}
