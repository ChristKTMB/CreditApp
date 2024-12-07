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
@Table(name = "demande")
public class Demande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private double montantDemande;
    private String butCredit;
    private String motifRejet;
    private Boolean statut;
    private int dureeMois;
    private LocalDateTime dateDemande;
    private LocalDateTime dateTraitement;

}
