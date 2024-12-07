package com.example.creditapp.controller;

import com.example.creditapp.model.Demande;
import com.example.creditapp.model.User;
import com.example.creditapp.securite.JwtService;
import com.example.creditapp.service.DemandeService;
import com.example.creditapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@AllArgsConstructor
@RestController
@RequestMapping(path = "demandes")
public class DemandeController {

    private final DemandeService demandeService;
    private final JwtService jwtService;
    private final UserService userService;

    private static final int ADMIN_ROLE_ID = 1; // Identifiant du rôle admin

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Demande>> getAllDemandes(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);
        Long userId = jwtService.extractUserId(token);
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = userOptional.get();
        if (user.getRole() == ADMIN_ROLE_ID) {
            // Si l'utilisateur est un admin, renvoyer toutes les demandes
            return ResponseEntity.ok(demandeService.getAllDemandes());
        } else {
            // Sinon, renvoyer seulement les demandes de cet utilisateur
            List<Demande> demandes = demandeService.getDemandesByUserId(userId);
            return ResponseEntity.ok(demandes);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getDemandeById(@PathVariable Long id) {
        Optional<Demande> demande = demandeService.getDemandeById(id);
        return demande.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity<Demande> addDemande(HttpServletRequest request, @RequestBody Demande demande) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String token = authorizationHeader.substring(7);
        Long userId = jwtService.extractUserId(token);

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOptional = userService.getUserById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        User user = userOptional.get();
        demande.setUser(user);
        demande.setDateDemande(LocalDateTime.now()); // Set the date of the request
        Demande savedDemande = demandeService.saveDemande(demande);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDemande);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Demande> updateDemande(@PathVariable Long id, @RequestBody Demande demande) {
        Optional<Demande> existingDemandeOptional = demandeService.getDemandeById(id);

        if (existingDemandeOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Demande existingDemande = existingDemandeOptional.get();

        // Conserver l'utilisateur existant
        demande.setUser(existingDemande.getUser());
        demande.setId(id); // Assurez-vous que l'ID est défini pour l'objet Demande à mettre à jour
        demande.setDateTraitement(LocalDateTime.now()); // Set the processing date
        existingDemande.setDateTraitement(LocalDateTime.now());

        // Vérifier si le statut est false et le motif du refus est fourni
        if (demande.getStatut() != null && !demande.getStatut() && (demande.getMotifRejet() == null || demande.getMotifRejet().isEmpty())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Demande updatedDemande = demandeService.saveDemande(demande);
        return ResponseEntity.ok(updatedDemande);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDemande(@PathVariable Long id) {
        demandeService.deleteDemande(id);
        return ResponseEntity.noContent().build();
    }
}
