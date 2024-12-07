package com.example.creditapp.service;

import com.example.creditapp.model.Demande;
import com.example.creditapp.repository.DemandeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class DemandeService {

    private final DemandeRepository demandeRepository;

    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    public Optional<Demande> getDemandeById(Long id) {
        return demandeRepository.findById(id);
    }

    public Demande saveDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    public void deleteDemande(Long id) {
        demandeRepository.deleteById(id);
    }

    public List<Demande> getDemandesByUserId(Long userId) {
        return demandeRepository.findByUserId(userId);
    }
}
