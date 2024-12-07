package com.example.creditapp.service;

import com.example.creditapp.TypeDeRole;
import com.example.creditapp.model.Role;
import com.example.creditapp.model.User;
import com.example.creditapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.creditapp.repository.RoleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public ResponseEntity<?> saveUser(User user) {
        try {
            // Validation de l'adresse e-mail
            String email = user.getEmail();
            if (!isValidEmail(email)) {
                throw new RuntimeException("Votre email est invalide!");
            }

            // Vérification de la disponibilité du nom d'utilisateur
            Optional<User> userOptional = userRepository.findByUsername(user.getUsername());
            if (userOptional.isPresent()) {
                throw new RuntimeException("Votre nom d'utilisateur est déjà utilisé");
            }

            // Vérification de la disponibilité de l'adresse e-mail
            userOptional = userRepository.findByEmail(email);
            if (userOptional.isPresent()) {
                throw new RuntimeException("Votre adresse e-mail est déjà utilisée");
            }

            // Cryptage du mot de passe
            String passwordCrypte = passwordEncoder.encode(user.getPassword());
            user.setPassword(passwordCrypte);

            // Définir le statut par défaut
            user.setStatus(true);

            // Définir la date de création
            user.setDateCreated(LocalDateTime.now());

            // Attribution du rôle par défaut (0 pour utilisateur simple)
            user.setRole(0);  // 0 pour "User", 1 pour "Admin"

            User savedUser = userRepository.save(user);

            // Répondre avec succès et renvoyer l'utilisateur sauvegardé en JSON
            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
        } catch (RuntimeException e) {
            // Capturer l'exception et renvoyer une réponse JSON avec le message d'erreur approprié
            String errorMessage = e.getMessage();
            if (errorMessage.contains("nom d'utilisateur est déjà utilisé")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Le nom d'utilisateur est déjà utilisé."));
            } else if (errorMessage.contains("adresse e-mail est déjà utilisée")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "L'adresse e-mail est déjà utilisée."));
            } else if (errorMessage.contains("email est invalide")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "L'adresse e-mail est invalide."));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Une erreur est survenue lors de l'enregistrement."));
            }
        }
    }

    // Méthode de validation d'email
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository
                .findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("Aucun utilisateur ne corespond a cet identifiant"));
    }
}
