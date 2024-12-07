package com.example.creditapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit")
public class Credit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "demande_id", nullable = false)
    private Demande demande;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double montantDemande;
    private double montantInteret;
    private double totalRembourser;
    private String statut;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;

}
