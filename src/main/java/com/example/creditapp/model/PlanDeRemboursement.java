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
@Table(name = "plan_de_remboursement")
public class PlanDeRemboursement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "credit_id", nullable = false)
    private Credit credit;

    private double montantParMois;
    private LocalDateTime dateEcheance;
    private boolean paye;

}
