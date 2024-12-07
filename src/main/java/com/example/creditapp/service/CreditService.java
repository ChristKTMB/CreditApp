package com.example.creditapp.service;

import com.example.creditapp.model.Credit;
import com.example.creditapp.repository.CreditRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class CreditService {

    private final CreditRepository creditRepository;

    public List<Credit> getAllCredits() {
        return creditRepository.findAll();
    }

    public Optional<Credit> getCreditById(Long id) {
        return creditRepository.findById(id);
    }

    public Credit saveCredit(Credit credit) {
        return creditRepository.save(credit);
    }

    public void deleteCredit(Long id) {
        creditRepository.deleteById(id);
    }
}
