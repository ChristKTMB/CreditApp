package com.example.creditapp.controller;

import com.example.creditapp.model.Type;
import com.example.creditapp.service.TypeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "types")
public class TypeController {

    private final TypeService typeService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public List<Type> getAllTypes() {
        return typeService.getAllTypes();
    }

    @GetMapping("/{id}")
    public Optional<Type> getTypeById(@PathVariable Long id) {
        return typeService.getTypeById(id);
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public Type addType(@RequestBody Type type) {
        return typeService.saveType(type);
    }

    @PutMapping("/{id}")
    public Type updateType(@PathVariable Long id, @RequestBody Type type) {
        type.setId(id); // Assurez-vous que l'ID est défini pour l'objet Type à mettre à jour
        return typeService.saveType(type);
    }

    @DeleteMapping("/{id}")
    public void deleteType(@PathVariable Long id) {
        typeService.deleteType(id);
    }
}
