package com.example.creditapp.controller;

import com.example.creditapp.model.PlanDeRemboursement;
import com.example.creditapp.service.PlanDeRemboursementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "plans")
public class PlanDeRemboursementController {

    private final PlanDeRemboursementService planDeRemboursementService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<PlanDeRemboursement> getAllPlans() {
        return planDeRemboursementService.getAllPlans();
    }

    @GetMapping("/{id}")
    public Optional<PlanDeRemboursement> getPlanById(@PathVariable Long id) {
        return planDeRemboursementService.getPlanById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public PlanDeRemboursement addPlan(@RequestBody PlanDeRemboursement planDeRemboursement) {
        return planDeRemboursementService.savePlan(planDeRemboursement);
    }

    @PutMapping("/{id}")
    public PlanDeRemboursement updatePlan(@PathVariable Long id, @RequestBody PlanDeRemboursement planDeRemboursement) {
        planDeRemboursement.setId(id); // Assurez-vous que l'ID est défini pour l'objet PlanDeRemboursement à mettre à jour
        return planDeRemboursementService.savePlan(planDeRemboursement);
    }

    @DeleteMapping("/{id}")
    public void deletePlan(@PathVariable Long id) {
        planDeRemboursementService.deletePlan(id);
    }
}
