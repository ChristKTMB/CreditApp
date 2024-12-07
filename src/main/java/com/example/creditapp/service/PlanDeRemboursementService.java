package com.example.creditapp.service;

import com.example.creditapp.model.PlanDeRemboursement;
import com.example.creditapp.repository.PlanDeRemboursementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class PlanDeRemboursementService {

    private final PlanDeRemboursementRepository planDeRemboursementRepository;

    public List<PlanDeRemboursement> getAllPlans() {
        return planDeRemboursementRepository.findAll();
    }

    public Optional<PlanDeRemboursement> getPlanById(Long id) {
        return planDeRemboursementRepository.findById(id);
    }

    public PlanDeRemboursement savePlan(PlanDeRemboursement planDeRemboursement) {
        return planDeRemboursementRepository.save(planDeRemboursement);
    }

    public void deletePlan(Long id) {
        planDeRemboursementRepository.deleteById(id);
    }
}
