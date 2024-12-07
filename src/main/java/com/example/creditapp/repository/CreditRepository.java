package com.example.creditapp.repository;

import com.example.creditapp.model.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
