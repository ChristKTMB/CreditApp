package com.example.creditapp.service;

import com.example.creditapp.model.Type;
import com.example.creditapp.repository.TypeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TypeService {

    private final TypeRepository typeRepository;

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getTypeById(Long id) {
        return typeRepository.findById(id);
    }

    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }
}
