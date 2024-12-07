package com.example.creditapp.repository;

import com.example.creditapp.TypeDeRole;
import com.example.creditapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByNom(String nom);
}

