package com.example.creditapp.repository;

import com.example.creditapp.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Long> {
    List<Demande> findByUserId(Long userId);
}