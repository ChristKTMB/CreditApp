package com.example.creditapp.controller;

import com.example.creditapp.model.Transaction;
import com.example.creditapp.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Transaction> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @GetMapping("/{id}")
    public Optional<Transaction> getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Transaction addTransaction(@RequestBody Transaction transaction) {
        // Set additional fields if necessary
        return transactionService.saveTransaction(transaction);
    }

    @PutMapping("/{id}")
    public Transaction updateTransaction(@PathVariable Long id, @RequestBody Transaction transaction) {
        transaction.setId(id); // Assurez-vous que l'ID est défini pour l'objet Transaction à mettre à jour
        return transactionService.saveTransaction(transaction);
    }

    @DeleteMapping("/{id}")
    public void deleteTransaction(@PathVariable Long id) {
        transactionService.deleteTransaction(id);
    }
}
