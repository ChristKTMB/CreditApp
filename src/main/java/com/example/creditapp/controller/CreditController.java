package com.example.creditapp.controller;

import com.example.creditapp.model.Credit;
import com.example.creditapp.service.CreditService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "credits")
public class CreditController {

    private final CreditService creditService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Credit> getAllCredits() {
        return creditService.getAllCredits();
    }

    @GetMapping("/{id}")
    public Optional<Credit> getCreditById(@PathVariable Long id) {
        return creditService.getCreditById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Credit addCredit(@RequestBody Credit credit) {
        // Set additional fields if necessary
        return creditService.saveCredit(credit);
    }

    @PutMapping("/{id}")
    public Credit updateCredit(@PathVariable Long id, @RequestBody Credit credit) {
        credit.setId(id); // Assurez-vous que l'ID est défini pour l'objet Credit à mettre à jour
        return creditService.saveCredit(credit);
    }

    @DeleteMapping("/{id}")
    public void deleteCredit(@PathVariable Long id) {
        creditService.deleteCredit(id);
    }
}
